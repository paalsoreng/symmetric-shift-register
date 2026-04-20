package part6

object VectorRepresentation {

    fun calculate(binary: List<Int>): List<Int> {

        val V = mutableListOf<Int>()
        var count = 0
        var lastBit = binary.first()

        for (bit in binary) {
            if (bit != lastBit) {
                V.add(count)
                count = 1
            } else {
                count++
            }
            lastBit = bit
        }
        V.add(count)

        if (binary.last() == 1) {
            V.add(0)
        }

        return V
    }
}
