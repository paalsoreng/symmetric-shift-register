package part7

import part6.VectorRepresentation

object NecessaryAssumptions {

    fun validate(binary: List<Int>, k: Int, p: Int) {

        val numberOfOneBits = binary.sum()
        val V = VectorRepresentation.calculate(binary)
        val (_, hMax) = MaximalityProperty.calculate(V)

        if (numberOfOneBits != k + p + 1) {
            throw IllegalArgumentException("w(A) = k + p + 1 is not fulfilled: w(A) = $numberOfOneBits, k=$k, p=$p")
        }

        if (p + 1 > hMax) {
            throw IllegalArgumentException("p + 1 <= hMax is not fulfilled: p=$p, hMax=$hMax")
        }
    }
}