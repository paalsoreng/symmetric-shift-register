package simulation

import part6.BinaryRepresentation

/**
 * Finds the period of a shift register with raw power (no calculation).
 */
object Simulation {

    /**
     * Goes in a loop and shifts the register until its back
     * into its start position. Returns the period found.
     */
    fun findMinimalPeriodForBinary(binary: List<Int>, p: Int, k: Int): Int {
        var period = 0
        var newBinary = binary

        do {
            newBinary = shift(newBinary, p, k)
            period++
        } while (binary != newBinary)

        return period
    }

    fun findMinimalPeriod(V: List<Int>, p: Int, k: Int): Int {
        val binary = BinaryRepresentation.calculate(V)
        return findMinimalPeriodForBinary(binary, p, k)
    }

    /**
     * Performs one shift and returns a new binary string.
     */
    fun shift(binary: List<Int>, p: Int, k: Int): List<Int> {
        val ω = ωSum(binary)

        val newBinary = binary.shiftLeft()

        val a1 = binary.first()
        val `a n+1` = if (k <= ω && ω <= k + p) a1.complement() else a1
        newBinary[newBinary.lastIndex] = `a n+1`

        return newBinary
    }

    private fun List<Int>.shiftLeft(): MutableList<Int> {
        if (this.isEmpty()) return mutableListOf()
        val newBinary = this.drop(1) + this.first()
        return newBinary.toMutableList()
    }

    private fun ωSum(binaryString: List<Int>): Int {
        val n = binaryString.size
        return binaryString.subList(1, n).sum()
    }

    private fun Int.complement(): Int {
        return when (this) {
            0 -> 1
            1 -> 0
            else -> throw IllegalStateException("Binary string must be 1 or 0")
        }
    }
}
