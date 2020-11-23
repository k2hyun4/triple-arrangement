package com.example.triplearrangement

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi

class LineAdapter(context: Context, val blocks: ArrayList<BlockType>, private var selected:Boolean = false) : BaseAdapter() {
    private val mContext: Context = context
    override fun getCount(): Int {
        return blocks.size
    }

    override fun getItem(position: Int): BlockType {
        return blocks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val blockView = layoutInflater.inflate(R.layout.view_block, viewGroup, false)

        val imageView = blockView.findViewById<ImageView>(R.id.image_view_block)
        val block = this.getItem(position)
        imageView.setImageResource(block.resourceId())
        imageView.clipToOutline = true

        val imageViewWrapper = blockView.findViewById<LinearLayout>(R.id.wrapper_block_image)
        val backgroundResourceId = if (selected && position + 1 == count) {
            R.drawable.block_rounding_highlighted
        } else {
            R.drawable.block_rounding
        }
        imageViewWrapper.setBackground(mContext.getDrawable(backgroundResourceId))

        return blockView
    }

    fun select() {
        this.selected = true
    }

    fun unselect() {
        this.selected = false
    }
}