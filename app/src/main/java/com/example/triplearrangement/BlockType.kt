package com.example.triplearrangement

enum class BlockType(private val resourceId: Int, val score: Int) {
    RED(R.drawable.block_red, 100),
    BLUE(R.drawable.block_blue, 200);
    // TODO: 2020-11-20 블럭 종류 추가 필요 
    // TODO: 2020-11-20 선택된 블럭 이미지 추가 필요 

    fun resourceId() = resourceId
}
