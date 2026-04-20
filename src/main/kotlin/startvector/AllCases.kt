package startvector

import part6.GeneralCase

object AllCases {

    fun findMinimalPeriod(binary: List<Int>, p: Int): Long {
        val k = StartVector.findValidK(binary, p)
        val validStartValue = StartVector.calculate(binary = binary, k = k, p = p)

        return GeneralCase.findMinimalPeriod(
            Vstart = validStartValue.v,
            p = validStartValue.p
        )
    }

}