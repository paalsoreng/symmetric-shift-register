package part2

object ShiftMap {

    fun calculate(v: List<Int>, e: Int): List<Int> {
        if (v.isEmpty()) return emptyList()
        val n = v.size
        val shift = ((e % n) + n) % n // ensure positive modulo
        return v.drop(shift) + v.take(shift)
    }
}