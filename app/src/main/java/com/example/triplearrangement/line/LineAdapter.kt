package com.example.triplearrangement.line

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.view.marginBottom
import com.example.triplearrangement.R
import com.example.triplearrangement.enums.BlockType

class LineAdapter(context: Context,
                  private val blocks: ArrayList<BlockType>,
                  private val blockHeight: Int,
                  private var selected: Boolean = false) : BaseAdapter() {
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
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(context)
        val blockView = layoutInflater.inflate(R.layout.view_block, viewGroup, false)
        val block = this.getItem(position)
        val imageViewWrapper = blockView.findViewById<LinearLayout>(R.id.wrapper_block_image)
        val imageViewWrapperLayoutParams = imageViewWrapper.layoutParams
        imageViewWrapperLayoutParams.height = blockHeight
        imageViewWrapper.layoutParams= imageViewWrapperLayoutParams

        val imageViewImageResourceId: Int
        val imageViewWrapperBackgroundResourceId: Int

        if (selected && position + 1 == count) {
            imageViewImageResourceId = block.selectedResourceId
            imageViewWrapperBackgroundResourceId = R.drawable.block_rounding_highlighted
        } else {
            imageViewImageResourceId = block.resourceId
            imageViewWrapperBackgroundResourceId = R.drawable.block_rounding
        }
        val background = context.getDrawable(imageViewWrapperBackgroundResourceId) as GradientDrawable
        background.cornerRadius = (blockHeight * 0.3).toFloat()
        imageViewWrapper.background = background

        val imageView = blockView.findViewById<ImageView>(R.id.image_view_block)
        val imageViewMarginParams = ViewGroup.MarginLayoutParams(imageView.layoutParams)
        val blockMargin = blockHeight / 10
        imageViewMarginParams.setMargins(blockMargin, blockMargin, blockMargin, blockMargin)
        imageView.layoutParams = LinearLayout.LayoutParams(imageViewMarginParams)
        imageView.setImageResource(imageViewImageResourceId)
        imageView.clipToOutline = true

        return blockView
    }

    fun select() {
        this.selected = true
    }

    fun deselect() {
        this.selected = false
    }
}