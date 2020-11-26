package com.example.triplearrangement.line

import android.content.Context
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import com.example.triplearrangement.PlayActivity
import com.example.triplearrangement.R
import com.example.triplearrangement.enums.LinePosition

class LineController(context: Context, private val lineWrappers: Array<RelativeLayout>) {
    private val lineAdapters = arrayListOf<LineAdapter>()
    private var selectedLine: LinePosition = LinePosition.NONE
    private val playActivity: PlayActivity = context as PlayActivity
    private val score = playActivity.score
    private val combo = playActivity.combo
    private val limitBlockCount = 12
    private val initRowCount = 4
    private val lines = arrayOf(
            Line(), Line(), Line()
    )

    init {
        for (targetLineIndex in lineWrappers.indices) {
            lineAdapters.add(LineAdapter(context, lines[targetLineIndex]))
            setLineAdapter(targetLineIndex)
            setOnClickLineCover(targetLineIndex)
        }

        setInitRow()
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

    fun addNewRow() {
        val level = score.getLevel()
        lines.forEach {line -> line.addNewBlock(level)}
        lineAdapters.forEach(LineAdapter::notifyDataSetChanged)
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
    }

    private fun deselectLine(prevSelectedLineIndex: Int) {
        if (prevSelectedLineIndex == LinePosition.NONE.index) {
            return
        }

        val prevLineAdapter = lineAdapters[prevSelectedLineIndex]
        prevLineAdapter.deselect()
        prevLineAdapter.notifyDataSetChanged()
        selectedLine = LinePosition.NONE
    }

    private fun setInitRow() {
        for (i in 1..initRowCount) {
            addNewRow()
        }
    }

    fun resetAll() {
        lines.forEach(Line::clear)
        deselectLine(selectedLine.index)
        lineAdapters.forEach(LineAdapter::notifyDataSetChanged)
        setInitRow()
    }

    fun checkLinesAddableBlock(): Boolean {
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
}