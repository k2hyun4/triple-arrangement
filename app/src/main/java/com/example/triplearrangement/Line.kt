package com.example.triplearrangement

import kotlin.random.Random

class Line(): ArrayList<BlockType>() {
    private val removeSize = 3
    /**
     * 블럭 이동시 발생
     */
    override fun add(element: BlockType): Boolean {
        super.add(element)

        if (this.size < removeSize) {
            return true
        }

        if (checkRemoveTarget(this.drop(this.size - removeSize))) {
            this.removeRange(this.size - removeSize, this.size)
        }

        return true
    }

    private fun checkRemoveTarget(targetBlocks: List<BlockType>): Boolean {
        return targetBlocks[0] == targetBlocks[1]
                && targetBlocks[1] == targetBlocks[2];
    }

    /**
     * 타임바 클릭시 발생, index : 0
     */
    fun addNewBlock(level: Int) {
        // TODO: 2020-11-23 현재 스코어에 따라 제한
        val score = 100_000
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