package com.bankirobot.palkinto.menu.ui.placeholder.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bankirobot.palkinto.R
import com.bankirobot.palkinto.menu.MenuController
import com.bankirobot.palkinto.utils.ConfiguredWebView
import kotlinx.android.synthetic.main.fragment_user_stats.*

class UserStatsFragment : Fragment() {
    private val handler = Handler(Looper.getMainLooper())
    private val pm10Dia =
        "http://${MenuController.DATE}"
    private val bothStat =
        "http://${MenuController.DATE}"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_user_stats, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        user_stats_refresh.setOnRefreshListener(swipe)
        val webView = ConfiguredWebView()
        webView.onWebCreate(user_stats_webview, pm10Dia, false)
        webView.onWebCreate(user_stats_webview2, bothStat, false)
    }

    private val swipe = SwipeRefreshLayout.OnRefreshListener {
        val webViews = arrayOf(user_stats_webview, user_stats_webview2)
        user_stats_refresh.isRefreshing = true
        for (webView in webViews) webView.reload()
        handler.postDelayed({ user_stats_refresh.isRefreshing = false }, 2000)

    }
}