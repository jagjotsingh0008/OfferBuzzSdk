package com.offerbuzz.ads

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.offerbuzz.ads.apis.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.provider.Settings
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.offerbuzz.ads.apis.InitializeCallback
import kotlinx.coroutines.withContext
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.offerbuzz.ads.apis.Apis
import com.offerbuzz.ads.apis.StartOfferCallback

class OfferBuzz(private val context: Context, private val appId:String, private val userId:String) {

    private suspend fun fetchGoogleAdId(context: Context): String? = withContext(Dispatchers.IO) {
        try {
            val info = AdvertisingIdClient.getAdvertisingIdInfo(context)
            if (!info.isLimitAdTrackingEnabled) info.id else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun initializeSdk(initializeCallback : InitializeCallback){
        CoroutineScope(Dispatchers.Main).launch {
            val gaid = fetchGoogleAdId(context) ?: ""
            try {
                val resp = RetrofitClient.service.sdkIniti(
                    appId      = appId,
                    googleAdId = gaid,
                    deviceId   = getDeviceId(context),
                    userId     = userId
                )
                if (resp.isSuccessful) {
                    val body = resp.body()
                    if (body?.status == true) {

                        val token   = body.token
                        val title   = body.title
                        val message = body.message

                        context.getSharedPreferences("prefs", MODE_PRIVATE)
                            .edit()
                            .putString("sdk_token", token)
                            .apply()

                        initializeCallback.onSuccess("$title $message")
                    } else {
                        Log.e("SDK_INIT", "Failed: ${body?.title}  ${body?.message}")
                        initializeCallback.onSuccess(body?.title+" "+body?.message)
                    }
                } else {
                    Log.e("SDK_INIT", "HTTP ${resp.code()}: ${resp.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("SDK_INIT", "Network error", e)
            }
        }

    }

    fun isAvailable(): Boolean {
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val token = prefs.getString("sdk_token", null)
        return !token.isNullOrEmpty()
    }

    fun startOffer(callback: StartOfferCallback) {
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val token = prefs.getString("sdk_token", null)
        if (token.isNullOrEmpty()) {
            callback.onError("SDK not initialized or token missing")
            return
        }
        val offerUrl = Apis.OFFER_URL+"?key=$token"

        try {
            context.openInCustomTab(offerUrl)
            callback.onSuccess()
        } catch (e: Exception) {
            callback.onError("Failed to open offer: ${e.message}")
        }
    }



    @SuppressLint("HardwareIds")
   private fun getDeviceId(context: Context): String =
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )

    private fun Context.openInCustomTab(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .build()

        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

}