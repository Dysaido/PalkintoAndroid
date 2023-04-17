package com.bankirobot.palkinto.menu.ui.settings

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bankirobot.palkinto.R
import com.bankirobot.palkinto.menu.MenuController
import com.bankirobot.palkinto.utils.SaveUserData
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MenuController.instance.tabVisibility = View.GONE
        val values = arrayOf(
            resources.getString(R.string.setting_account),
            resources.getString(R.string.setting_support),
            resources.getString(R.string.setting_notifications),
            resources.getString(R.string.setting_change_password),
            resources.getString(R.string.setting_dark_mode),
            resources.getString(R.string.setting_log_out),
        )
        val adapterView = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            values
        )
        settings_list.adapter = adapterView
        settings_list.onItemClickListener = onClick

    }

    private val onClick = AdapterView.OnItemClickListener { _, _, position, _ ->
        when (position) {
            0 -> findNavController().navigate(R.id.action_settings_to_account)
            1 -> findNavController().navigate(R.id.action_nav_settings_to_nav_support)
            2 -> findNavController().navigate(R.id.action_nav_settings_to_nav_notifications)
            3 -> findNavController().navigate(R.id.action_nav_settings_to_nav_change_password)
            4 -> Log.w("SettingsFragment", "onClick::4")
            5 -> {
                SaveUserData(requireContext()).clearUser()
                handler.postDelayed(requireActivity()::finishAffinity, 2000)
            }
            else -> Log.w("SettingsFragment", "onClick::else")
        }
    }
}