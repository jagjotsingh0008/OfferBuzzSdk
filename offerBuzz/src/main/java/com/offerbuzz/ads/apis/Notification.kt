package com.offerbuzz.ads.apis

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.provider.Settings
import android.util.Log
import com.offerbuzz.ads.models.CustomDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Notification(private val context: Context) {

    fun check(tokenID:String){
        CoroutineScope(Dispatchers.Main).launch {

            try {
                val resp = RetrofitClient.service.sendNotification(
                    token  = tokenID
                )
                if (resp.isSuccessful) {
                    val body = resp.body()
                    if (body?.status == true) {

                        val title   = body.title
                        val message = body.message

                        CustomDialog(context).showCustomDialog(title,message)

                    }
                } else {
                    Log.e("SDK_INIT", "HTTP ${resp.code()} ${resp.message()}")
                }
            } catch (e: Exception) {
                Log.e("SDK_INIT", "Network error", e)
            }
        }

    }

    @SuppressLint("HardwareIds")
    private fun getDeviceId(context: Context): String =
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )



}