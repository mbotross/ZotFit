package com.example.zotfit.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.zotfit.R

class Item_Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item__details)

        val webView: WebView = findViewById(R.id.webView)
        val bundle = intent.extras
        val url = bundle?.getString("url")
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
        webView.settings.javaScriptEnabled = true
        webView.settings.supportZoom()
    }
}