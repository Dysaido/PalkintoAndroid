package com.bankirobot.palkinto.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bankirobot.palkinto.R
import kotlinx.android.synthetic.main.fragment_first_steps.*

class FirstStepsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_first_steps, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        first_steps_button.setOnClickListener { findNavController().navigate(R.id.action_first_steps_to_about_palkinto) }
    }

}