package startvector

import part6.VectorRepresentation

data class ValidStartValue(
    val binary: List<Int>,
    val k: Int,
    val p: Int,
    val v: List<Int> = VectorRepresentation.calculate(binary)
)

object StartVector {

    fun calculate(binary: List<Int>, k: Int, p: Int): ValidStartValue {
        var currentBinary = binary
        val binaryList = mutableListOf<List<Int>>()
        val maxNecessarySteps = binary.size * 3

        binaryList.add(currentBinary)

        repeat(maxNecessarySteps) {
            currentBinary = nextBinary(binary = currentBinary, k = k, p = p)
            binaryList.add(currentBinary)
        }

        val binaryWithWList = extendWithW(binaryList = binaryList, k = k)

        val startVector = findStartVector(binaryWithWList)

        return startVector
    }

    private fun nextBinary(binary: List<Int>, k: Int, p: Int): List<Int> {
        val sum = binary.drop(1).sum()

        if (k <= sum && sum <= k + p) {
            val newLastBit = 1 - binary.first()
            return binary.drop(1) + newLastBit
        } else {
            return binary.drop(1) + binary.first()
        }
    }

    private fun extendWithW(binaryList: List<List<Int>>, k: Int): List<BinaryAndW> {
        return binaryList.map {
            BinaryAndW(
                binary = it,
                w = it.sum() - k
            )
        }
    }

    private fun findStartVector(binaryAndWList: List<BinaryAndW>): ValidStartValue {
        val maxW = binaryAndWList.maxOf { it.w }
        val minW = binaryAndWList.minOf { it.w }

        val lastIndexOfMinW = binaryAndWList.indexOfLast { it.w == minW }

        for (i in lastIndexOfMinW downTo 0) {
            val binaryAndW = binaryAndWList[i]
            if (binaryAndW.w == maxW) {
                val newP = maxW - minW - 1
                val newK = findValidK(binaryAndW.binary, newP)

                return ValidStartValue(
                    binary = binaryAndW.binary,
                    k = newK,
                    p = newP
                )
            }
        }
        throw IllegalArgumentException("No valid start vector found")
    }

    fun findValidK(binary: List<Int>, p: Int): Int {
        val k = binary.sum() - (p + 1)
        return k
    }
}

private data class BinaryAndW(
    val binary: List<Int>,
    val w: Int
)
