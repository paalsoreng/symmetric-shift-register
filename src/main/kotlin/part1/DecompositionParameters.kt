package part1

object DecompositionParameters {

    fun calculate(v: List<Int>): List<Int> {

        val r = mutableListOf<Int>()
        val s = v.size
        var j = 0
        var rNext = 0
        r.add(0)

        while (rNext < s) {
            val tNext = tMax(v, r[j])
            rNext = r[j] + 2 * tNext + 1
            r.add(rNext)
            j++
        }

        return r
    }

    private fun tMax(v: List<Int>, rStart: Int): Int {
        return InternalIndexFunction.calculate(v, rStart)
    }
}