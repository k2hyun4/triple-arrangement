package com.example.triplearrangement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*

class MainActivity : AppCompatActivity() {
    var selectedLine: LinePosition = LinePosition.NONE
    val lineAdapters = arrayListOf<LineAdapter>()

    val lines = arrayOf<Line>(
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

        for (i in lineWrappers.indices) {
            val lineListView = lineWrappers[i].findViewById<ListView>(R.id.line)
            lineAdapters.add(LineAdapter(this, lines[i]))
            lineListView.adapter = lineAdapters[i]
            val lineCover = lineWrappers[i].findViewById<LinearLayout>(R.id.line_cover)
            lineCover.setOnClickListener {
                if (selectedLine.equals(LinePosition.NONE)) {
                    if (lines[i].size > 0) {
                        selectedLine = findLinePosition(i)
                        lineAdapters[i].select()
                    }
                } else if (i == selectedLine.index) {
                    selectedLine = LinePosition.NONE
                    lineAdapters[i].unselect()
                } else {
                    //블럭 데이터 처리
                    val prevLine = lines[selectedLine.index]
                    val newLine = lines[i]
                    newLine.add(prevLine.last())

                    prevLine.removeAt(prevLine.lastIndex)

                    //블럭 뷰 처리
                    val prevLineAdapter = lineAdapters[selectedLine.index]
                    prevLineAdapter.unselect()
                    prevLineAdapter.notifyDataSetChanged()

                    selectedLine = LinePosition.NONE
                }

                lineAdapters[i].notifyDataSetChanged()
            }
        }

        findViewById<LinearLayout>(R.id.time_bar)
                .setOnClickListener {
                    lines.forEach(Line::addNewBlock)
                    lineAdapters.forEach(LineAdapter::notifyDataSetChanged)
                }
    }

    private fun findLinePosition(index: Int) : LinePosition {
        return LinePosition.values()[index]
    }
}