package part8

object StartVector {

    fun calculate(V: List<Int>, k: Int, p: Int): MutableList<Int> {

        StartVectorAssumptions.validate(V, k, p)

        val t = V.size
        val maxValue = 3 * t

        val (b, q, e) = InfiniteVectorRepresentation.calculate(V, k, p, maxValue)

        val j = findLeastJ(b, p)
        val m = findLeastM(b, j)
        val jMax = findMaxJ(b, j, m, p)

        val start = jMax + 1
        val end = jMax + t - 1

        val startVector = q.subList(start - 1, end).toMutableList()
        startVector.add(e[j])

        return startVector
    }

    private fun findLeastJ(b: List<Int>, p: Int): Int {
        for (i in b.indices) {
            if (b[i] == p + 1) {
                return i
            }
        }
        throw IllegalStateException("could not find least j")
    }

    private fun findLeastM(b: List<Int>, j: Int): Int {
        var m = j + 1

        while (m < b.size) {
            if (b[m] == 0) {
                return m
            }
            m++
        }
        throw IllegalStateException("could not find least m")
    }

    private fun findMaxJ(b: List<Int>, j: Int, m: Int, p: Int): Int {
        var jMax = j
        var i = j

        while (i <= m - 1) {
            if (b[i] == p + 1) {
                jMax = i
            }
            i++
        }
        return jMax
    }
}

