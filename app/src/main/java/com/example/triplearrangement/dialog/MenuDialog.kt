package com.example.triplearrangement.dialog

import android.content.Context
import android.widget.Button
import com.example.triplearrangement.R

class MenuDialog(private val context: Context): CustomDialog(context = context, layoutResourceId = R.layout.dialog_menu){
    init {
        dialog.findViewById<Button>(R.id.resume)
                .setOnClickListener {
                    dialog.dismiss()
                    playActivity.addNewRowTimer.start()
                }
    }

    override fun show() {
        if (!playActivity.addNewRowTimer
                        .ready()) {
            return
        }

        super.show()
        playActivity.addNewRowTimer.stop()
    }
}