package relativeweight

import part6.BinaryRepresentation


object RelativeWeight {

    fun calculate(V: List<Int>, p: Int, k: Int, length: Int): List<Int> {
        val bitString = BinaryRepresentation.calculate(V)
        val sum = bitString.sum()

        if (k > sum) throw IllegalArgumentException("k cannot be greater than the number of 1s in the bit string")
        if (sum > k + p + 1) throw IllegalArgumentException("The number of 1s in the bit string cannot be greater than k + p + 1")

        val w0 = sum - k
        val A = bitString.toMutableList()
        val W = mutableListOf(w0)

        repeat(length) { i ->
            val a = A[i]
            val w = W[i]
            val (nextW, nextA) = step(w, a, p)
            A.add(nextA)
            W.add(nextW)
        }

        return W
    }

    private fun step(w: Int, a: Int, p: Int): Pair<Int, Int> = when {
        w == 0 && a == 1 -> Pair(0, 1)
        w in 1..p + 1 && a == 1 -> Pair(w - 1, 0)
        w == p + 1 && a == 0 -> Pair(p + 1, 0)
        w in 0 until p + 1 && a == 0 -> Pair(w + 1, 1)
        else -> throw IllegalArgumentException("Invalid state: w=$w, a=$a")
    }

}

