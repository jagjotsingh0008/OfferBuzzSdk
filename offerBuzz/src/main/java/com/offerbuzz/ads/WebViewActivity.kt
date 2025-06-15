package com.offerbuzz.ads

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.offerbuzz.ads.databinding.ActivityWebViewBinding
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView


class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.webView.webViewClient = WebViewClient()

        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(binding.webView, true)

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val cookies = cookieManager.getCookie(url)
                println("Cookies for $url: $cookies")
            }
        }

//        binding.webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
//                val url = request.url.toString()
//
//
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                startActivity(intent)
//                return true
//            }
//
//            @Deprecated("Deprecated in Java")
//            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                url?.let {
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
//                    startActivity(intent)
//                }
//                return true
//            }
//        }


        binding.webView.loadUrl(intent.getStringExtra("url") ?: "https://offerbuzz.in")

    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}