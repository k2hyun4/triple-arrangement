package com.example.triplearrangement.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import android.widget.Button
import com.example.triplearrangement.MainActivity
import com.example.triplearrangement.PlayActivity
import com.example.triplearrangement.R

open class CustomDialog(private val context: Context, private val layoutResourceId: Int) {
    protected val dialog = Dialog(context)
    protected val playActivity: PlayActivity

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(layoutResourceId)
        playActivity= context as PlayActivity

        dialog.findViewById<Button>(R.id.restart)
                .setOnClickListener {
                    playActivity.resetAll()
                    dialog.dismiss()
                }
        dialog.findViewById<Button>(R.id.goToMain)
                .setOnClickListener {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    dialog.dismiss()
                    context.startActivity(intent)
                }
    }

    open fun show() {
        dialog.show()
    }
}