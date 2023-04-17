package com.bankirobot.palkinto.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bankirobot.palkinto.R
import kotlinx.android.synthetic.main.fragment_about_palkinto.*

class AboutPalkintoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_about_palkinto, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        about_palkinto_back.setOnClickListener { requireActivity().onBackPressed() }
        about_palkinto_continue.setOnClickListener { findNavController().navigate(R.id.action_about_palkinto_to_authorize) }
    }

}