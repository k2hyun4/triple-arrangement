package com.murkgom.triple_arrangement.dialog

import android.content.Context
import android.widget.Button
import android.widget.Switch
import com.murkgom.triple_arrangement.R

class MenuDialog(context: Context, startOptionComboModeFlag: Boolean): CustomDialog(context = context, layoutResourceId = R.layout.dialog_menu){
    init {
        val resumeButton = dialog.findViewById<Button>(R.id.resume)
        resumeButton.setOnClickListener {
            dialog.dismiss()
            playActivity.addNewRowTimer.start()
        }

        val comboModeSwitch = dialog.findViewById<Switch>(R.id.switch_combo_mode)
        comboModeSwitch.isChecked = startOptionComboModeFlag
        comboModeSwitch.setOnCheckedChangeListener {_, isChecked ->
            playActivity.lineController
                    .switchComboMode(isChecked)
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