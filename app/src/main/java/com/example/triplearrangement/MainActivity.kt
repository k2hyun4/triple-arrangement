package com.example.triplearrangement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var mSelectedLine: LinePosition = LinePosition.NONE
    val mLineAdapters = arrayListOf<LineAdapter>()

    val mLines = arrayOf<Line>(
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
            mLineAdapters.add(LineAdapter(this, mLines[i]))
            lineListView.adapter = mLineAdapters[i]
            val lineCover = lineWrappers[i].findViewById<LinearLayout>(R.id.line_cover)
            lineCover.setOnClickListener {
                if (mSelectedLine.equals(LinePosition.NONE)) {
                    if (mLines[i].size > 0) {
                        mSelectedLine = findLinePosition(i)
                        mLineAdapters[i].select()
                    }
                } else if (i == mSelectedLine.index) {
                    mSelectedLine = LinePosition.NONE
                    mLineAdapters[i].unselect()
                } else {
                    //블럭 데이터 처리
                    val prevLine = mLines[mSelectedLine.index]
                    val newLine = mLines[i]
                    newLine.add(prevLine.last())

                    prevLine.removeAt(prevLine.lastIndex)

                    //블럭 뷰 처리
                    val prevLineAdapter = mLineAdapters[mSelectedLine.index]
                    prevLineAdapter.unselect()
                    prevLineAdapter.notifyDataSetChanged()

                    mSelectedLine = LinePosition.NONE
                }

                mLineAdapters[i].notifyDataSetChanged()
            }
        }

        findViewById<LinearLayout>(R.id.time_bar)
                .setOnClickListener {
                    mLines.forEach(Line::addNewBlock)
                    mLineAdapters.forEach(LineAdapter::notifyDataSetChanged)
                }
    }

    private fun findLinePosition(index: Int) : LinePosition {
        return LinePosition.values()[index]
    }
}