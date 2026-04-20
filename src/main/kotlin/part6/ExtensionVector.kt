package part6

object ExtensionVector {

    fun calculate(V: List<Int>): MutableList<Int> {
        val extV = V.toMutableList()
        extV[extV.lastIndex] = extV.last() + 1
        return extV
    }
}