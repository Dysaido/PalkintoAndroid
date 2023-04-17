package com.bankirobot.palkinto.menu.ui.settings.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bankirobot.palkinto.R
import com.bankirobot.palkinto.utils.SaveUserData
import kotlinx.android.synthetic.main.controller_menu.*
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var saveUserData: SaveUserData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_account, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        saveUserData = SaveUserData(requireContext())
        requireActivity().nav_bottom.isVisible = false
        if (saveUserData.userID.isNotEmpty()) {
            acc_fullName.text = "${saveUserData.lastName} ${saveUserData.firstName}"
            acc_user_id.text = saveUserData.userID
            acc_email.text = saveUserData.email
        }
//        acc_exit.setOnClickListener(clickExit)
    }

//    private val clickExit = View.OnClickListener {
////        requireActivity().menu_appbar.isVisible = false
//        it.isEnabled = false
//        saveUserData.clearUser()
//        handler.postDelayed(requireActivity()::finishAffinity, 2000)
//    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().nav_bottom.isVisible = true
    }
}