package trace

data class Report(
    val inputParams: InputParams,
    val contractList: List<Contract>,
    val stepList: List<Step>
)

data class InputParams(
    val p: Int,
    val v0: List<Int>
)

data class Contract(
    val pi: List<Int>,
    val tau: List<Int>,
    val r: List<Int>
)

data class Step(
    val v: List<Int>,
    val j: Long,
    val zeta: Long,
    val gamma: Int? = null,
    val c: List<Int>? = null,
    val tau: List<Int>? = null,
    val d: List<Int>? = null,
    val alpha: Int? = null,
    val alphaStar: Int? = null,
    val gammaStar: Int? = null,
    val g: Long? = null,
    val x: Long? = null,
    val y: Long? = null
)