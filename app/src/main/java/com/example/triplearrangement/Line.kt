package com.example.triplearrangement

import kotlin.random.Random

class Line(): ArrayList<BlockType>() {
    val REMOVE_SIZE = 3
    /**
     * 블럭 이동시 발생
     */
    override fun add(element: BlockType): Boolean {
        super.add(element)

        if (this.size < REMOVE_SIZE) {
            return true
        }

        if (checkRemoveTarget(this.drop(this.size - REMOVE_SIZE))) {
            this.removeRange(this.size - REMOVE_SIZE, this.size)
        }

        return true
    }

    private fun checkRemoveTarget(targetBlocks: List<BlockType>): Boolean {
        return targetBlocks[0].equals(targetBlocks[1])
                && targetBlocks[1].equals(targetBlocks[2]);
    }

    /**
     * 타임바 클릭시 발생, index : 0
     */
    fun addNewBlock() {
        // TODO: 2020-11-23 현재 스코어에 따라 제한
        val score = 100_000
        val values = BlockType.values()
        var newBlock: BlockType = values[Random.nextInt(values.size)]
        val exceptBlock = this.getExceptBlock()

        while (newBlock.score > score || newBlock.equals(exceptBlock)) {
            newBlock = values[Random.nextInt(values.size)]
        }

        super.add(0, newBlock)
    }

    /**
     * 타임바를 통한 새 블럭 추가시 제외해야 할 블럭 체크
     * 없을 경우, null 리턴
     */
    private fun getExceptBlock(): BlockType? {
        if (this.size < REMOVE_SIZE - 1) {
            return null
        }

        val firstBlockType = this.get(0)

        if (firstBlockType.equals(this.get(1))) {
            return firstBlockType
        } else {
            return null
        }
    }
}