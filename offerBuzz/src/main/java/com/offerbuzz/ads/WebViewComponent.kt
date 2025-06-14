package com.offerbuzz.ads

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.constraintlayout.widget.ConstraintLayout
import com.offerbuzz.ads.databinding.ViewWebviewBinding

@SuppressLint("SetJavaScriptEnabled")
class WebViewComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    // ① Inflate with attachToRoot = true
    private val binding = ViewWebviewBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    init {
        binding.webview.apply {
            // ② Basic setup
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT

            // ③ Intercept offer URLs and open externally
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    val url = request.url.toString()
                    return if (url.startsWith("https://offerbuzz.in/offerwall/info")) {
                        view.context.startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        )
                        true
                    } else {
                        false
                    }
                }
            }
        }
    }

    /** Load any URL into the WebView */
    fun loadUrl(url: String) {
        binding.webview.loadUrl(url)
    }
}
