package com.murkgom.triple_arrangement.line

import com.murkgom.triple_arrangement.enums.BlockType
import kotlin.random.Random

class Line(): ArrayList<BlockType>() {
    private val removeSize = 3
    /**
     * 블럭 이동시 발생
     */

    fun checkWillArrangement(newBlock: BlockType): Boolean {
        if (this.size < removeSize - 1) {
            return false
        }

        val lastBlock = this.last()

        if (lastBlock != newBlock) {
            return false
        }

        return lastBlock == this[this.lastIndex - 1]
    }

    fun arrange() {
        this.removeRange(this.size - removeSize + 1, this.size)
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