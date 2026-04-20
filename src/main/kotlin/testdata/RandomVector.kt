package testdata

import kotlin.random.Random

object RandomVector {

    fun generateRandomVectorList(count: Int, maxLength: Int, maxSameBits: Int): List<List<Int>> {
        val vectorList = mutableListOf<List<Int>>()
        repeat(count) {
            vectorList += generateRandomVector(maxLength, maxSameBits)
        }
        return vectorList.distinct()
    }

    private fun generateRandomVector(maxLength: Int, maxSameBits: Int): List<Int> {
        val length = Random.nextInt(2, maxLength + 1)
        val liste = MutableList(length) { Random.Default.nextInt(1, maxSameBits + 1) }

        if (liste.size % 2 != 0) {
            liste.add(0)
        }
        return liste
    }
}