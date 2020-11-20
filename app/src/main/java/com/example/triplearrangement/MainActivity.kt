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
                    mLineAdapters[i].selected = true
                    // TODO: 2020-11-20 mSelectedLine 값 변경
                    mLineAdapters[i].notifyDataSetChanged()
                } else {
                    // TODO: 2020-11-20 move 처리
                }
            }
        }

        findViewById<LinearLayout>(R.id.time_bar)
                .setOnClickListener {
                    lines.forEach {line -> line.add(0, getRandomBlock())}
                    mLineAdapters.forEach(LineListAdapter::notifyDataSetChanged)
                }
    }

    fun getRandomBlock(): BlockType {
        val values = BlockType.values()
        return values[Random.nextInt(values.size)]
    }
}