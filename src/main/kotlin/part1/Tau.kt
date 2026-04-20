package part1

object Tau {

    fun calculate(v: List<Int>): List<Int> {

        val tau = mutableListOf<Int>()

        tau.add(0)

        for (j in v.indices) {
            val t = tau[j] + v[j] - 1  // index 0 in Kotlin
            tau.add(t)
        }
        return tau
    }
}