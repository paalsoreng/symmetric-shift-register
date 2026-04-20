package part8

import kotlin.math.min

object InfiniteVectorRepresentation {

    fun calculate(V: List<Int>, k: Int, p: Int, maxValue: Int): InfiniteVectorRepresentationResponse {

        val bVector = bVectorFrom(V, k)
        val qVector = qVectorFrom(V)
        val eVector = eVectorFrom(V)
        val sVector = mutableListOf<Int>()

        for (i in 0..maxValue) {
            val qi = qVector[i]
            val bi = bVector[i]

            val s: Int
            val b: Int

            if (i.isEven()) {
                s = min(qi, bi)
                b = bi - s
            } else {
                s = min(qi, (p + 1 - bi))
                b = bi + s
            }

            val e = qi - s
            val q = eVector[i] + s

            sVector.add(s)
            bVector.add(b)
            eVector.add(e)
            qVector.add(q)
        }

        return InfiniteVectorRepresentationResponse(
            b = bVector, q = qVector, e = eVector
        )
    }

    private fun bVectorFrom(V: List<Int>, k: Int): MutableList<Int> {
        val w = Weigth.calculate(V)
        val b0 = w - k
        return mutableListOf(b0)
    }

    private fun qVectorFrom(V: List<Int>): MutableList<Int> {
        val q = copyOf(V)
        q.removeLast()
        return q
    }

    private fun eVectorFrom(V: List<Int>): MutableList<Int> {
        val e0 = V.last()
        return mutableListOf(e0)
    }

    private fun copyOf(V: List<Int>) = V.toMutableList()

}

private fun Int.isEven(): Boolean {
    return this % 2 == 0
}

data class InfiniteVectorRepresentationResponse(
    val b: List<Int>,
    val q: List<Int>,
    val e: List<Int>
)
