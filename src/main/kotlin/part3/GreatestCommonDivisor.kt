package part3

object GreatestCommonDivisor {

    fun calculate(x: Int, y: Int): Int {
        return euclidsAlgorithm(x, y)
    }

    fun calculate(x: Long, y: Long): Long {
        return euclidsAlgorithm(x, y)
    }

    private fun euclidsAlgorithm(n1: Int, n2: Int): Int {
        if (n2 == 0) {
            return n1
        }
        return euclidsAlgorithm(n2, n1 % n2)
    }

    private fun euclidsAlgorithm(n1: Long, n2: Long): Long {
        if (n2 == 0L) {
            return n1
        }
        return euclidsAlgorithm(n2, n1 % n2)
    }

}