package com.example.triplearrangement.record

import android.widget.TextView

class Combo(private val view: TextView) {
    private var maxCombo: Int = 0
    private var moveCount = 0
    private val allowedSurMove = 0

    private fun getCombo(): Int {
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
        }
    }

    fun addCombo() {
        setCombo(getCombo() + 1)
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
    }
}