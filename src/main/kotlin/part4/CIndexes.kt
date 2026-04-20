package part4

object CIndexes {

    fun calculate(V: List<Int>): List<Int> {

        val c = mutableListOf<Int>()

        var cValue = 0
        c.add(cValue)

        while(true) {
            val value = IndexFunction.calculate(V, cValue)

            if (value > 0) {
                cValue = value
                c.add(cValue)
            } else {
                return c
            }
        }
    }
}