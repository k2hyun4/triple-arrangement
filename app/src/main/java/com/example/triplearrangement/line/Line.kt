package com.example.triplearrangement.line

import com.example.triplearrangement.enums.BlockType
import kotlin.random.Random

class Line(): ArrayList<BlockType>() {
    private val removeSize = 3
    /**
     * 블럭 이동시 발생
     */
    fun checkAndRemoveIfAligned(): Boolean {
        var alignmentFlag = false

        if (this.size > removeSize - 1
                && checkRemoveTarget(this.drop(this.size - removeSize))) {
            this.removeRange(this.size - removeSize, this.size)
            alignmentFlag = true
        }

        return alignmentFlag
    }

    private fun checkRemoveTarget(targetBlocks: List<BlockType>): Boolean {
        return targetBlocks[0] == targetBlocks[1]
                && targetBlocks[1] == targetBlocks[2];
    }

    /**
     * 타임바 클릭시 발생, index : 0
     */
    fun addNewBlock(level: Int) {
        val values = BlockType.values()
        var newBlock: BlockType = values[Random.nextInt(values.size)]
        val exceptBlock = this.getExceptBlock()

        while (newBlock.level > level || newBlock == exceptBlock) {
            newBlock = values[Random.nextInt(values.size)]
        }

        super.add(0, newBlock)
    }

    /**
     * 타임바를 통한 새 블럭 추가시 제외해야 할 블럭 체크
     * 없을 경우, null 리턴
     */
    private fun getExceptBlock(): BlockType? {
        if (this.size < removeSize - 1) {
            return null
        }

        val firstBlockType = this[0]

        return if (firstBlockType == this[1]) {
            firstBlockType
        } else {
            null
        }
    }
}