package part5

import part3.ProgressionParameters
import part4.DistanceVector
import trace.AlgorithmicFunctionExecution
import trace.ExecutionLog

object AlgorithmicFunction {

    fun calculate(
        V: List<Int>,
        jStar: Long,
        betaStar: Long,
        log: ExecutionLog? = null
    ): AlgorithmicFunctionResponse {

        val afe = AlgorithmicFunctionExecution()
        log?.algorithmicExecutionList?.add(afe)
        afe.V = V

        if (V.hasNoOnesInInterior()) {
            afe.gamma = 0
            afe.r = jStar
            afe.beta = betaStar + jStar
            return AlgorithmicFunctionResponse(r = jStar, beta = betaStar + jStar)
        }

        val D = DistanceVector.calculate(V, afe)
        val alpha = ParameterAlpha.calculate(V)

        val (alphaStar, gammaStar) = ProgressionParameters.calculate(D, alpha)

        val (x, y) = LeastSolution.calculate(alphaStar, betaStar, afe)

        val r = (2L * x * gammaStar) + (y * jStar)
        val beta = (y * betaStar) + r

        afe.D = D
        afe.alpha = alpha
        afe.alphaStar = alphaStar
        afe.gammaStar = gammaStar
        afe.x = x
        afe.y = y
        afe.r = r
        afe.beta = beta
        afe.gamma = D.size

        return AlgorithmicFunctionResponse(r, beta)
    }
}

fun List<Int>.hasNoOnesInInterior(): Boolean {
    val s = this.size
    return this.subList(1, s - 1).none { it == 1 }
}

data class AlgorithmicFunctionResponse(
    val r: Long,
    val beta: Long
)
