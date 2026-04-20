package part3

object AuxiliaryFunction {

    fun calculate(D: List<Int>, α: Int): Int {
        return D.count { it <= α }
    }
}