package com.example.triplearrangement.record

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.triplearrangement.PlayActivity
import com.example.triplearrangement.R

class Combo(context: Context,
            private val view: TextView) {
    private val playActivity: PlayActivity = context as PlayActivity
    private var maxCombo: Int = 0
    private var moveCount = 0
    private val allowedSurMove = 0
    private val comboModeStandardCount = 10
    private var comboModeFlag = false
    private val linesWrapper: LinearLayout
    private val comboModeImgLeft: ImageView
    private val comboModeImgRight: ImageView
    private val timeBar: LinearLayout

    init {
        linesWrapper = playActivity.findViewById(R.id.wrapper_lines)
        comboModeImgLeft = playActivity.findViewById(R.id.img_combo_mode_left)
        comboModeImgRight = playActivity.findViewById(R.id.img_combo_mode_right)
        timeBar = playActivity.findViewById(R.id.time_bar)
    }

    fun getCombo(): Int {
        return this.view.text
                .toString()
                .toInt()
    }

    fun getMaxCombo(): Int {
        checkMaxCombo()
        return this.maxCombo
    }

    fun checkMaintain(level: Int) {
        if (this.moveCount > level + allowedSurMove) {
            checkMaxCombo()
            setCombo(0)
            stopComboMode()
        }
    }

    fun inComboMode(): Boolean {
        return this.comboModeFlag
    }

    private fun startComboMode() {
        if (comboModeFlag
            || this.getCombo() < comboModeStandardCount) {
            return
        }

        comboModeFlag = true
        comboModeEffect()
    }

    private fun stopComboMode() {
        comboModeFlag = false
        defaultEffect()
    }

    private fun defaultEffect() {
        linesWrapper.background = playActivity.getDrawable(R.color.black)
        comboModeImgLeft.visibility = View.INVISIBLE
        comboModeImgRight.visibility = View.INVISIBLE
        timeBar.background = playActivity.getDrawable(R.color.purple_700)
    }

    private fun comboModeEffect() {
        linesWrapper.background = playActivity.getDrawable(R.drawable.background_combo_mode)
        comboModeImgLeft.visibility = View.VISIBLE
        comboModeImgRight.visibility = View.VISIBLE
        timeBar.background = playActivity.getDrawable(R.color.red)
    }

    fun addCombo() {
        setCombo(getCombo() + 1)
        startComboMode()
    }

    private fun setCombo(combo: Int) {
        this.view.text = (combo).toString()
        resetMoveCount()
    }

    private fun checkMaxCombo() {
        val nowCombo = getCombo()
        maxCombo = if (nowCombo > maxCombo) {
            nowCombo
        } else {
            maxCombo
        }
    }

    fun addMoveCount() {
        this.moveCount += 1
    }

    fun resetMoveCount() {
        this.moveCount = 0
    }

    fun resetAll() {
        this.maxCombo = 0
        setCombo(0)
        stopComboMode()
    }
}