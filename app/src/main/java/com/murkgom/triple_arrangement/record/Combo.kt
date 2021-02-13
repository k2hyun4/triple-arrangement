package com.murkgom.triple_arrangement.record

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.murkgom.triple_arrangement.PlayActivity
import com.murkgom.triple_arrangement.R
import com.murkgom.triple_arrangement.enums.BlockType
import org.w3c.dom.Text

class Combo(context: Context,
            private val view: TextView) {
    private val playActivity: PlayActivity = context as PlayActivity
    private var maxCombo: Int = 0
    private val allowedSurMove = 0
    private val comboModeStandardCount = 10
    private var comboModeFlag = false

    private val initMaxMoveCountInCombo = BlockType.values().last().level + allowedSurMove + 2
    private var combo = 0
    private var moveCount = 0
    private var moveCountInCombo = 0

    private val linesWrapper: LinearLayout = playActivity.findViewById(R.id.wrapper_lines)
    private val comboModeImgLeft: ImageView = playActivity.findViewById(R.id.img_combo_mode_left)
    private val comboModeImgRight: ImageView = playActivity.findViewById(R.id.img_combo_mode_right)
    private val timeBar: RelativeLayout = playActivity.findViewById(R.id.time_bar)
    private val moveCountInComboView: TextView = playActivity.findViewById(R.id.move_count_in_combo)

    init {
        resetMoveCountInCombo()
    }

    fun getCombo(): Int {
        return this.combo
    }

    fun getMaxCombo(): Int {
        checkMaxCombo()
        return this.maxCombo
    }

    fun checkMaintain(level: Int) {
        if (this.moveCount > level + allowedSurMove) {
            checkMaxCombo()
            resetCombo()
        }
    }

    fun inComboMode(): Boolean {
        return this.comboModeFlag
    }

    private fun startComboMode() {
        if (comboModeFlag || combo < comboModeStandardCount) {
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
        setCombo(combo + 1)
        startComboMode()
        resetMoveCountInCombo()
    }

    private fun setCombo(combo: Int) {
        this.combo = combo
        this.view.text = combo.toString()
        resetMoveCount()
    }

    private fun resetCombo() {
        setCombo(0)
        resetMoveCountInCombo()
        stopComboMode()
    }

    private fun checkMaxCombo() {
        val nowCombo = combo
        maxCombo = if (nowCombo > maxCombo) {
            nowCombo
        } else {
            maxCombo
        }
    }

    fun addMoveCount() {
        this.moveCount += 1
        minusMoveCountInCombo()
    }

    fun resetMoveCount() {
        this.moveCount = 0
        resetMoveCountInCombo()
    }

    fun resetAll() {
        resetMoveCount()
        this.maxCombo = 0
        resetCombo()
    }

    private fun setMoveCountInCombo(moveCount: Int) {
        moveCountInCombo = moveCount
        moveCountInComboView.text = playActivity.getString(R.string.move_count_in_combo, moveCount)
        moveCountInComboView.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                (initMaxMoveCountInCombo - moveCount / 1.5f) * 3f)
    }

    fun addMoveCountInCombo() {
        //level up
        this.setMoveCountInCombo(moveCountInCombo + 1)
    }

    private fun minusMoveCountInCombo() {
        //일반적인 move
        this.setMoveCountInCombo(moveCountInCombo - 1)
    }

    private fun resetMoveCountInCombo() {
        //case1: timeBar
        //case2: combo break
        //case3: combo add
        this.setMoveCountInCombo(playActivity.score
                .getLevel() + allowedSurMove + 1)
    }
}