package com.example.triplearrangement

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.*

class MainActivity : AppCompatActivity() {
    val lines = arrayOf(
            arrayListOf("11", "22"),
            arrayListOf(),
            arrayListOf()
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wrappers = arrayOf<RelativeLayout>(
            findViewById(R.id.wrapper_line_1),
            findViewById(R.id.wrapper_line_2),
            findViewById(R.id.wrapper_line_3)
        )

        for (i in wrappers.indices) {
            val lineListView = wrappers[i].findViewById<ListView>(R.id.line)
            val adapter = ListViewAdapter(this, lines[i])
            lineListView.adapter = adapter
            val lineCover = wrappers[i].findViewById<LinearLayout>(R.id.line_cover)
            lineCover.setOnClickListener {
                lines[i].add("1")
                adapter.notifyDataSetChanged()
            }
        }
    }

    private class ListViewAdapter(context: Context, val blocks: ArrayList<String>) : BaseAdapter() {
        private val mContext: Context = context
        override fun getCount(): Int {
            return blocks.size
        }

        override fun getItem(position: Int): Any {
            return blocks.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)

            return layoutInflater.inflate(R.layout.view_block, viewGroup, false)
        }

    }
}