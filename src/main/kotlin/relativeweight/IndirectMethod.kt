package relativeweight

import part6.BinaryRepresentation
import part6.VectorRepresentation

object IndirectMethod {

    fun findNewVectorAndParameters(q: List<Int>, p: Int, k: Int): CalculatedVectorPAndK {
        val a = BinaryRepresentation.calculate(q)
        val n = a.size
        val wA = a.sum()

        val (w0, aSeq) = weightSequence(a, k, p, n, wA)

        val minW = w0.min()
        val maxW = w0.max()

        val pNew = maxW - minW - 1
        val kNew = minW + k

        val w = w0.map { it - minW }

        // s: first index with w == pNew+1, t: next index after s with w == 0,
        // r: last index in [s, t-1] with w == pNew+1
        val s = firstIndexOf(pNew + 1, 0, w)
        val t = firstIndexOf(0, s + 1, w)
        val r = lastIndexOf(pNew + 1, s, t - 1, w)

        val aNew = aSeq.subList(r, r + n)
        val qNew = VectorRepresentation.calculate(aNew)

        return CalculatedVectorPAndK(qNew, pNew, kNew)
    }

    fun findPRange(q: List<Int>): Pair<Int, Int> {
        val n = BinaryRepresentation.calculate(q).size
        return Pair(0, n - 1)
    }

    fun findKRange(q: List<Int>, p: Int): Pair<Int, Int> {
        val binary = BinaryRepresentation.calculate(q)
        val n = binary.size
        val sum = binary.sum()
        return Pair(maxOf(0, sum - (p + 1)), minOf(sum, n - (p + 1)))
    }

    private fun weightSequence(
        a0: List<Int>, k: Int, p: Int, n: Int, wA: Int
    ): Pair<List<Int>, List<Int>> {
        val w = mutableListOf(wA - k)
        val a = a0.toMutableList()
        for (j in 0..4 * n) {
            val (aNext, wNext) = aux(a[j], w[j], p)
            a.add(aNext)
            w.add(wNext)
        }
        return Pair(w, a)
    }

    private fun aux(a: Int, w: Int, p: Int): Pair<Int, Int> = when {
        a == 0 && w == p + 1 -> Pair(0, p + 1)
        a == 0 && w < p + 1 && w >= 0 -> Pair(1, w + 1)
        a == 1 && w == 0 -> Pair(1, 0)
        a == 1 && w <= p + 1 && w > 0 -> Pair(0, w - 1)
        else -> throw IllegalArgumentException("Invalid state: a=$a, w=$w, p=$p")
    }

    private fun firstIndexOf(m: Int, start: Int, w: List<Int>): Int {
        for (j in start until w.size) if (w[j] == m) return j
        throw IllegalStateException("Value $m not found in weight sequence from index $start")
    }

    private fun lastIndexOf(m: Int, start: Int, last: Int, w: List<Int>): Int {
        for (j in last downTo start) if (w[j] == m) return j
        throw IllegalStateException("Value $m not found in weight sequence between $start and $last")
    }

}

data class CalculatedVectorPAndK(
    val v: List<Int>,
    val p: Int,
    val k: Int
)