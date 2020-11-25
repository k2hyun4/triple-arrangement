package com.example.triplearrangement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var selectedLine: LinePosition = LinePosition.NONE
    private val addNewBlockPeriodMillis: Long = 2_500
    private val limitBlockCount = 12
    private val lineAdapters = arrayListOf<LineAdapter>()

    private lateinit var score: Score
    private lateinit var combo: Combo
    private lateinit var lineWrappers: Array<RelativeLayout>
    private lateinit var addNewBlockTimer: Timer
    private lateinit var gameOverDialog: GameOverDialog

    private val lines = arrayOf(
        Line(), Line(), Line()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        score = Score(findViewById(R.id.score))
        combo = Combo(findViewById(R.id.combo))
        lineWrappers = arrayOf(
            findViewById(R.id.wrapper_line_left),
            findViewById(R.id.wrapper_line_middle),
            findViewById(R.id.wrapper_line_right)
        )

        for (targetLineIndex in lineWrappers.indices) {
            lineAdapters.add(LineAdapter(this, lines[targetLineIndex]))
            setLineAdapter(targetLineIndex)
            setOnClickLineCover(targetLineIndex)
        }

        setOnClickTimeBar()
        startAddNewBlockTimer()
        initGameOverDialog()
    }

    private fun startAddNewBlockTimer() {
        addNewBlockTimer = timer(period = addNewBlockPeriodMillis) {
            runOnUiThread {
                if (!checkLinesAddableBlock()) {
                    gameOver()
                }

                addNewLine()
            }
        }
    }

    private fun setLineAdapter(targetLineIndex: Int) {
        lineWrappers[targetLineIndex]
            .findViewById<ListView>(R.id.line)
            .adapter = lineAdapters[targetLineIndex]
    }

    private fun setOnClickLineCover(targetLineIndex: Int) {
        lineWrappers[targetLineIndex].findViewById<LinearLayout>(R.id.line_cover)
            .setOnClickListener {
                val lineAdapter = lineAdapters[targetLineIndex]

                if (selectedLine == LinePosition.NONE) {
                    if (lines[targetLineIndex].size > 0) {
                        selectedLine = findLinePosition(targetLineIndex)
                        lineAdapter.select()
                    }
                } else if (targetLineIndex == selectedLine.index) {
                    selectedLine = LinePosition.NONE
                    lineAdapter.deselect()
                } else {
                    moveBlock(targetLineIndex)
                }

                lineAdapter.notifyDataSetChanged()
            }
    }

    private fun setOnClickTimeBar() {
        findViewById<LinearLayout>(R.id.time_bar)
                .setOnClickListener {
                    if (checkLinesAddableBlock()) {
                        addNewLine()
                        score.plusForTimeBarClick()
                        combo.resetMoveCount()
                    }
                }
    }

    private fun addNewLine() {
        val level = score.getLevel()
        lines.forEach {line -> line.addNewBlock(level)}
        lineAdapters.forEach(LineAdapter::notifyDataSetChanged)
    }

    private fun deselectLine(prevSelectedLineIndex: Int) {
        val prevLineAdapter = lineAdapters[prevSelectedLineIndex]
        prevLineAdapter.deselect()
        prevLineAdapter.notifyDataSetChanged()
    }

    private fun moveBlock(targetLineIndex: Int) {
        val newLine = lines[targetLineIndex]

        if (!checkLineAddableBlock(newLine)) {
            return
        }

        val prevLine = lines[selectedLine.index]
        val prevLineLastBlockType = prevLine.last()
        newLine.add(prevLineLastBlockType)

        combo.addMoveCount()

        if (newLine.checkAndRemoveIfAligned()) {
            score.plusForAlignment()
            combo.addCombo()
        } else {
            combo.checkMaintain(score.getLevel())
        }

        prevLine.removeAt(prevLine.lastIndex)
        score.plusForMoveBlock()
        deselectLine(selectedLine.index)
        selectedLine = LinePosition.NONE
    }

    private fun checkLinesAddableBlock(): Boolean {
        var addableFlag = true

        for (line in lines) {
            if (!checkLineAddableBlock(line)) {
                addableFlag = false
                break;
            }
        }

        return addableFlag
    }

    private fun checkLineAddableBlock(line: Line): Boolean {
        return line.size < limitBlockCount
    }

    private fun findLinePosition(targetLineIndex: Int) : LinePosition {
        return LinePosition.values()[targetLineIndex]
    }

    private fun initGameOverDialog() {
        gameOverDialog = GameOverDialog(this)
        this.supportFragmentManager.executePendingTransactions()
        gameOverDialog.getDialog().setOnDismissListener {
            resetAll()
        }
    }

    private fun gameOver() {
        addNewBlockTimer.cancel()
        gameOverDialog.run(score.getScore(), combo.getCombo())
    }

    fun resetAll() {
        score.resetAll()
        combo.resetAll()
        lines.forEach(Line::clear)
        lineAdapters.forEach(LineAdapter::notifyDataSetChanged)

        startAddNewBlockTimer()
    }
}