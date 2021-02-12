package com.example.triplearrangement.record

import android.util.Log
import android.widget.TextView
import kotlin.math.*

class Score(private val view: TextView, private val combo: Combo) {
    private val initLevel = 1
    private val initLevelUpStandardScore = 500
    private var level: Int = initLevel
    private var levelUpStandardScore = initLevelUpStandardScore

    private val timeBarClickBonus = 10
    private val moveBlockBonus = 5
    private val alignmentBonus = 50

    fun getScore(): Int {
        return this.view.text
                .toString()
                .replace(",", "")
                .toInt()
    }

    private fun addScore(addScore: Int) {
        var buffedAddScore = addScore * level * combo.comboBonus()
        setScore(this.getScore() + buffedAddScore)
    }

    private fun setScore(score: Int) {
        this.view.text = "%,d".format(score)
        checkLevelUp(score)
    }

    private fun checkLevelUp(score: Int) {
        if (score > levelUpStandardScore) {
            level += 1
            //levelUpStandardScore *= log(level * 2.0, 4.0).roundToInt()
            levelUpStandardScore = 1_000 * (level * 1.0).pow(2.4).roundToInt() - 1_000
        }
    }

    fun plusForTimeBarClick() {
        this.addScore(timeBarClickBonus)
    }

    fun plusForMoveBlock() {
        this.addScore(moveBlockBonus)
    }

    fun plusForAlignment() {
        this.addScore(alignmentBonus)
    }

    fun getLevel(): Int {
        return this.level
    }

    fun resetAll() {
        this.level = initLevel
        this.levelUpStandardScore = initLevelUpStandardScore
        setScore(0)
    }
}