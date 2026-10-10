package api

import relativeweight.DirectMethod

class DirectMethodApi {

    fun findPeriod(v: List<Int>, p: Int, k: Int): Int {
        return DirectMethod.findPeriod(v, p, k)
    }

    fun findKRange(v: List<Int>, p: Int): Pair<Int, Int> {
        return DirectMethod.findKRange(v, p)
    }

    fun findPRange(v: List<Int>): Pair<Int, Int> {
        return DirectMethod.findPRange(v)
    }

}