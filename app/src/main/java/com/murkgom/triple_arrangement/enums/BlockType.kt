package com.murkgom.triple_arrangement.enums

import com.murkgom.triple_arrangement.R

enum class BlockType(val resourceId: Int, val selectedResourceId:Int, val level: Int) {
    RED(R.drawable.block_red, R.drawable.block_red_selected, 1),
    BLUE(R.drawable.block_blue, R.drawable.block_blue_selected,1),
    YELLOW(R.drawable.block_yellow, R.drawable.block_yellow_selected, 2),
    GREEN(R.drawable.block_green, R.drawable.block_green_selected, 3),
    ORANGE(R.drawable.block_orange, R.drawable.block_orange_selected, 4),
    PURPLE(R.drawable.block_purple, R.drawable.block_purple_selected,  5),
    WHITE(R.drawable.block_white, R.drawable.block_white_selected, 6),
    HIGH_RED(R.drawable.block_high_red, R.drawable.block_high_red_selected, 7),
    HIGH_BLUE(R.drawable.block_high_blue, R.drawable.block_high_blue_selected, 8),
    HIGH_YELLOW(R.drawable.block_high_yellow, R.drawable.block_high_yellow_selected, 9),
    HIGH_GREEN(R.drawable.block_high_green, R.drawable.block_high_green_selected, 10);
}