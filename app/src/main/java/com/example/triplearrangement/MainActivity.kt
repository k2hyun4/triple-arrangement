package com.example.triplearrangement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*

class MainActivity : AppCompatActivity() {
    var selectedLine: LinePosition = LinePosition.NONE
    var level: Int = 1

    private val lineAdapters = arrayListOf<LineAdapter>()

    private val lines = arrayOf<Line>(
            Line(), Line(), Line()
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lineWrappers = arrayOf<RelativeLayout>(
            findViewById(R.id.wrapper_line_left),
            findViewById(R.id.wrapper_line_middle),
            findViewById(R.id.wrapper_line_right)
        )

        for (targetLineIndex in lineWrappers.indices) {
            val lineListView = lineWrappers[targetLineIndex].findViewById<ListView>(R.id.line)
            lineAdapters.add(LineAdapter(this, lines[targetLineIndex]))
            lineListView.adapter = lineAdapters[targetLineIndex]
            val lineCover = lineWrappers[targetLineIndex].findViewById<LinearLayout>(R.id.line_cover)
            lineCover.setOnClickListener {
                if (selectedLine == LinePosition.NONE) {
                    if (lines[targetLineIndex].size > 0) {
                        selectedLine = findLinePosition(targetLineIndex)
                        lineAdapters[targetLineIndex].select()
                    }
                } else if (targetLineIndex == selectedLine.index) {
                    selectedLine = LinePosition.NONE
                    lineAdapters[targetLineIndex].deselect()
                } else {
                    moveBlock(targetLineIndex)
                    deselectLine(selectedLine.index)
                    selectedLine = LinePosition.NONE
                }

                lineAdapters[targetLineIndex].notifyDataSetChanged()
            }
        }

        findViewById<LinearLayout>(R.id.time_bar)
                .setOnClickListener {
                    lines.forEach{line -> line.addNewBlock(level)}
                    lineAdapters.forEach(LineAdapter::notifyDataSetChanged)
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
        newLine.add(prevLine.last())

        prevLine.removeAt(prevLine.lastIndex)
    }

    private fun findLinePosition(targetLineIndex: Int) : LinePosition {
        return LinePosition.values()[targetLineIndex]
    }
}