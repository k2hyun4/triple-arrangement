package com.example.triplearrangement

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.annotation.RequiresApi
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var mSelectedLine: LinePosition = LinePosition.NONE
    val mLineAdapters = arrayListOf<LineListAdapter>()

    val lines = arrayOf(
            arrayListOf(BlockType.RED),
            arrayListOf(BlockType.BLUE),
            arrayListOf(BlockType.BLUE)
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
            mLineAdapters.add(LineListAdapter(this, lines[i]))
            lineListView.adapter = mLineAdapters[i]
            val lineCover = lineWrappers[i].findViewById<LinearLayout>(R.id.line_cover)
            lineCover.setOnClickListener {
                if (mSelectedLine.equals(LinePosition.NONE)) {
                    mSelectedLine = findLinePosition(i)
                    mLineAdapters[i].select()
                } else if (i == mSelectedLine.index) {
                    mSelectedLine = LinePosition.NONE
                    mLineAdapters[i].unselect()
                } else {
                    //블럭 데이터 처리
                    val prevLine = lines[mSelectedLine.index]
                    val newLine = lines[i]
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
                    lines.forEach {line -> line.add(0, getRandomBlock())}
                    mLineAdapters.forEach(LineListAdapter::notifyDataSetChanged)
                }
    }

    private fun getRandomBlock(): BlockType {
        // TODO: 2020-11-23 현재 스코어에 따라 제한
        val values = BlockType.values()
        return values[Random.nextInt(values.size)]
    }

    private fun findLinePosition(index: Int) : LinePosition {
        return LinePosition.values()[index]
    }
}