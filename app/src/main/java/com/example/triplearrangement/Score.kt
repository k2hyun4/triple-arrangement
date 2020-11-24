package com.example.triplearrangement

import android.widget.TextView

class Score(private val view: TextView) {
    private var level: Int = 1
    private val timeBarClickBonus = 10
    private val moveBlockBonus = 5
    private val alignmentBonus = 50
    private var levelUpStandardScore = 1_000

    private fun getScore(): Int {
        return this.view.text
                .toString()
                .replace(",", "")
                .toInt()
    }

    private fun addScore(addScore: Int) {
        setScore(this.getScore() + addScore)
    }

    private fun setScore(score: Int) {
        this.view.text = "%,d".format(score)
        checkLevelUp(score)
    }

    private fun checkLevelUp(score: Int) {
        if (score > levelUpStandardScore) {
            level += 1
            levelUpStandardScore *= 2
        }
    }

    fun plusForTimeBarClick() {
        this.addScore(timeBarClickBonus)
    }

    fun plusForMoveBlock() {
        this.addScore(moveBlockBonus * level)
    }

    fun plusForAlignment() {
        this.addScore(alignmentBonus * level)
    }

    fun getLevel(): Int {
        return this.level
    }
}