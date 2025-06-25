package com.offerbuzz.ads

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.provider.Settings
import android.view.View
import com.offerbuzz.ads.databinding.ActivityCheckingBinding
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


class CheckingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckingBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val offerUrl = intent.getStringExtra("url")

        if (isDeveloperOptionsEnabled()) {
            binding.devMode.visibility = View.VISIBLE
            binding.appBlockVpn.visibility = View.GONE
        } else {
            if (isVpnActive(  )){
                binding.devMode.visibility = View.GONE
                binding.appBlockVpn.visibility = View.VISIBLE

                binding.title.text = "VPN Connection Detected"
                binding.message.text = "We’ve detected that you’re connected through a VPN or proxy, which restricts access to our Offerwall. To continue earning rewards, please disable your VPN and relaunch the app."
            }else{
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("url", offerUrl)
                startActivity(intent)
                finish()
            }

        }

    }

    @SuppressLint("ObsoleteSdkInt")
    fun Context.isDeveloperOptionsEnabled(): Boolean {
        return try {
            val enabled = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Settings.Global.getInt(
                    contentResolver,
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
                    0
                )
            } else {
                @Suppress("DEPRECATION")
                Settings.Secure.getInt(
                    contentResolver,
                    Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED,
                    0
                )
            }
            enabled == 1
        } catch (e: Settings.SettingNotFoundException) {
            false
        }
    }

    private fun Context.isVpnActive(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // For API 23+ we can directly check transports on all networks:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.allNetworks.forEach { network ->
                cm.getNetworkCapabilities(network)
                    ?.takeIf { caps -> caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN) }
                    ?.let { return true }
            }
            return false
        }

        // Fallback for older devices: check “vpn” interfaces via NetworkInfo
        @Suppress("DEPRECATION")
        cm.allNetworkInfo.forEach { info ->
            if (info.typeName.equals("VPN", ignoreCase = true) && info.isConnected) {
                return true
            }
        }
        return false
    }

}