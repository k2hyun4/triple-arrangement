package com.example.triplearrangement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*

class MainActivity : AppCompatActivity() {
    var selectedLine: LinePosition = LinePosition.NONE
    lateinit var score: Score
    lateinit var combo: Combo
    lateinit var lineWrappers: Array<RelativeLayout>
    private val lineAdapters = arrayListOf<LineAdapter>()

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
                    val level = score.getLevel()
                    lines.forEach{line -> line.addNewBlock(level)}
                    lineAdapters.forEach(LineAdapter::notifyDataSetChanged)
                    score.plusForTimeBarClick()
                }
    }

    private fun deselectLine(prevSelectedLineIndex: Int) {
        val prevLineAdapter = lineAdapters[prevSelectedLineIndex]
        prevLineAdapter.deselect()
        prevLineAdapter.notifyDataSetChanged()
    }

    private fun moveBlock(targetLineIndex: Int) {
        val prevLine = lines[selectedLine.index]
        val newLine = lines[targetLineIndex]
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

    private fun findLinePosition(targetLineIndex: Int) : LinePosition {
        return LinePosition.values()[targetLineIndex]
    }
}