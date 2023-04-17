package com.bankirobot.palkinto

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bankirobot.palkinto.R
import com.google.firebase.database.snapshot.BooleanNode

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        var NETWORK_VALIDATION = false
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.controller_welcome)
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.w(TAG, "The default network is now: " + network)
                NETWORK_VALIDATION = true
            }

            override fun onLost(network: Network) {
                Log.w(TAG, "The application no longer has a default network. The last default network was " + network)
                NETWORK_VALIDATION = false
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                Log.w(TAG, "The default network changed capabilities: " + networkCapabilities)
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                Log.w(TAG, "The default network changed link properties: " + linkProperties)
            }
        })

    }

}