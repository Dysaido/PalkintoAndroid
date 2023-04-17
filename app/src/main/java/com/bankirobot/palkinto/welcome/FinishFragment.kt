package com.bankirobot.palkinto.welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bankirobot.palkinto.R
import com.bankirobot.palkinto.uservalidation.UserController
import com.bankirobot.palkinto.utils.SaveUserData
import kotlinx.android.synthetic.main.fragment_finish.*

class FinishFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_finish, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        finish_continue.setOnClickListener {
            SaveUserData(requireContext()).firstLaunch = false
            startActivity(Intent(requireContext(), UserController::class.java))
            requireActivity().finish()
        }
    }

}