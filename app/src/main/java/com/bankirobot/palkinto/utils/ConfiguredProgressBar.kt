package com.bankirobot.palkinto.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View

class ConfiguredProgressBar(context: Context, view: View) {
    private val dialog = Dialog(context)

    fun start() = dialog.show()

    fun stop() = dialog.cancel()

    init {
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}