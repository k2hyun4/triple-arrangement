package com.murkgom.triple_arrangement.record

import android.widget.TextView
import android.widget.Toast
import com.murkgom.triple_arrangement.PlayActivity
import com.murkgom.triple_arrangement.enums.BlockType
import kotlin.math.*

class Score(private val view: TextView,
            private val playActivity: PlayActivity) {
    private var initLevel = 1
    private var initLevelUpStandardScore = 500
    private var initScore = 0
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

    private fun comboBonus(): Int {
        if (!playActivity.combo.burning()) {
            return 1
        }

        return playActivity.combo.getCombo() / 20 + 1
    }

    private fun addScore(addScore: Int) {
        val buffedAddScore = addScore * level * comboBonus()
        setScore(this.getScore() + buffedAddScore)
    }

    private fun setScore(score: Int) {
        this.view.text = "%,d".format(score)
        checkLevelUp(score)
    }

    private fun levelUpable(): Boolean {
        return level < BlockType.values()
            .last()
            .level
    }

    private fun checkLevelUp(score: Int) {
        if (!levelUpable()) {
            return
        }

        if (score > levelUpStandardScore) {
            level += 1
            playActivity.combo
                    .addMoveCountInBurning()
            levelUpStandardScore = getLevelStandardScore(level)
            Toast.makeText(playActivity,
                    String.format("레벨%d!", level),
                    Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun getLevelStandardScore(level: Int): Int {
        if (level == 1) {
            return 500
        }

        return (102.361 * (1.0* level).pow(3.766)).roundToInt() + 3004
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
        setScore(initScore)
    }

    fun setting(startLevel: Int) {
        initLevel = startLevel
        initLevelUpStandardScore = getLevelStandardScore(startLevel)
        initScore = if (startLevel == 1) {0} else {getLevelStandardScore(startLevel - 1)}
        resetAll()
    }
}