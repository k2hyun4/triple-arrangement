package com.example.triplearrangement

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi

class LineAdapter(context: Context, private val blocks: ArrayList<BlockType>, private var selected:Boolean = false) : BaseAdapter() {
    private val context: Context = context
    override fun getCount(): Int {
        return blocks.size
    }

    override fun getItem(position: Int): BlockType {
        return blocks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(context)
        val blockView = layoutInflater.inflate(R.layout.view_block, viewGroup, false)

        val block = this.getItem(position)
        val imageView = blockView.findViewById<ImageView>(R.id.image_view_block)
        val imageViewWrapper = blockView.findViewById<LinearLayout>(R.id.wrapper_block_image)
        val imageViewImageResourceId: Int
        val imageViewWrapperBackgroundResourceId: Int

        if (selected && position + 1 == count) {
            imageViewImageResourceId = block.selectedResourceId
            imageViewWrapperBackgroundResourceId = R.drawable.block_rounding_highlighted
        } else {
            imageViewImageResourceId = block.resourceId
            imageViewWrapperBackgroundResourceId = R.drawable.block_rounding
        }

        imageView.setImageResource(imageViewImageResourceId)
        imageView.clipToOutline = true
        imageViewWrapper.background = context.getDrawable(imageViewWrapperBackgroundResourceId)

        return blockView
    }

    fun select() {
        this.selected = true
    }

    fun deselect() {
        this.selected = false
    }
}