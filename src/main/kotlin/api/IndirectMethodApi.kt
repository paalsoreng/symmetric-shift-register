package api

import relativeweight.CalculatedVectorPAndK
import relativeweight.IndirectMethod

class IndirectMethodApi {

    fun findNewVectorAndParameters(v: List<Int>, p: Int, k: Int): CalculatedVectorPAndK {
        return IndirectMethod.findNewVectorAndParameters(v, p, k)
    }

    fun findKRange(v: List<Int>, p: Int): Pair<Int, Int> {
        return IndirectMethod.findKRange(v, p)
    }

    fun findPRange(v: List<Int>): Pair<Int, Int> {
        return IndirectMethod.findPRange(v)
    }

}
