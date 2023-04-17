package com.bankirobot.palkinto.utils

import android.annotation.SuppressLint
import android.webkit.*

@SuppressLint("ClickableViewAccessibility")
class ConfiguredWebView {

    @SuppressLint("SetJavaScriptEnabled")
    fun onWebCreate(webView: WebView, url: String, touch: Boolean) {
        val webSettings = webView.settings
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webSettings.domStorageEnabled = true
        webSettings.javaScriptEnabled = true
        webView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean = true
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                view.loadUrl("file:///android_asset/www/index.html")
            }
        }
        if (!touch) webView.setOnTouchListener { _, _ -> true }
        webView.clearHistory()
        webView.loadUrl(url)
    }
}