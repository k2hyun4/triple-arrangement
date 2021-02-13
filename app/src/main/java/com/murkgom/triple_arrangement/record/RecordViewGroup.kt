package com.murkgom.triple_arrangement.record

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.murkgom.triple_arrangement.R

class RecordViewGroup {
    lateinit var recordView: TextView
    lateinit var newSign: TextView

    constructor(layout: LinearLayout) {
        recordView = layout.findViewById(R.id.record)
        newSign = layout.findViewById(R.id.new_sign)
    }

    fun setRecord(record: Int) {
        recordView.text = "%,d".format(record)
    }

    fun getRecord(): Int {
        return recordView.text
                .toString()
                .replace(",", "")
                .toInt()
    }

    fun showNewSign() {
        newSign.visibility = View.VISIBLE
    }

    fun hideNewSign() {
        newSign.visibility = View.INVISIBLE
    }
}