package simulation.fastalgorithm

fun calculateKBounds(n: Int, p: Int, asum: Int): Pair<Int, Int> {
    val kmin = maxOf(0, asum - (p + 1))
    val kmax = minOf(asum, n - (p + 1))
    return Pair(kmin, kmax)
}

fun findMinimalPeriod(ain: List<Int>, p: Int, k: Int): Long? {
    val w = ain.sum().toLong() - k.toLong()
    val max = 10_000_000L
    var a = ain.toMutableList()
    var wCurrent = w
    var counter = 0L

    while (counter < max) {
        val (a_new, w_new) = aux(a[0], wCurrent, p)
        a = shift(a, a_new)
        wCurrent = w_new
        counter++
        if (a == ain) {
            return counter
        }
    }
    return null
}

private fun shift(A: MutableList<Int>, anew: Int): MutableList<Int> {
    val B = A.toMutableList()
    B.add(anew)
    B.removeAt(0)
    return B
}

private fun aux(a: Int, w: Long, p: Int): Pair<Int, Long> {
    val pLong = p.toLong()
    return when {
        a == 0 && w == pLong + 1 -> Pair(0, pLong + 1)
        a == 0 && w < pLong + 1 && w >= 0 -> Pair(1, w + 1)
        a == 1 && w == 0L -> Pair(1, 0L)
        a == 1 && w <= pLong + 1 && w > 0 -> Pair(0, w - 1)
        else -> Pair(1000, 10001L)
    }
}

