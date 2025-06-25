package com.offerbuzz.ads.models

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.offerbuzz.ads.R

class CustomDialog(private val context: Context) {

    fun showCustomDialog(title:String,coins:String) {

        val dialog = Dialog(context)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.pop)
        dialog.setCancelable(true)

        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

        val tvTitle = dialog.findViewById<TextView>(R.id.title)
        val tvCoins = dialog.findViewById<TextView>(R.id.coins)

        tvTitle.text = title
        tvCoins.text = coins

        dialog.show()
    }

}