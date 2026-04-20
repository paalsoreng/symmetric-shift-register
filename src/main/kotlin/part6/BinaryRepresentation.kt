package part6

/**
 * Transforms a vector representation back to binary representation.
 */
object BinaryRepresentation {

    fun calculate(V: List<Int>): List<Int> {
        val binary = mutableListOf<Int>()

        for (i in V.indices) {
            when (i % 2) {
                0 -> binary += ones(V[i])
                1 -> binary += zeroes(V[i])
            }
        }
        return binary
    }

    private fun ones(count: Int): List<Int> = (1..count).map { 1 }

    private fun zeroes(count: Int): List<Int> = (1..count).map { 0 }
}

