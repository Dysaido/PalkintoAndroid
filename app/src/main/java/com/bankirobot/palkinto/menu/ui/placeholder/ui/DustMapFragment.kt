package com.bankirobot.palkinto.menu.ui.placeholder.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bankirobot.palkinto.R
import com.bankirobot.palkinto.menu.MenuController
import com.bankirobot.palkinto.utils.ConfiguredWebView
import kotlinx.android.synthetic.main.fragment_dust_map.*

class DustMapFragment : Fragment() {
    private val handler = Handler(Looper.getMainLooper())
    private val pm10Map = "http://${MenuController.DATE}"

    private fun runWebs() {
        val webView = ConfiguredWebView()
        webView.onWebCreate(dust_map_webview, pm10Map, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_dust_map, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dust_map_refresh.setOnRefreshListener {
            dust_map_refresh.isRefreshing = true
            dust_map_webview.reload()
            handler.postDelayed({ dust_map_refresh.isRefreshing = false }, 2000)
        }
        runWebs()
    }
}