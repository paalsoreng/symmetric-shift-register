package trace

class ExecutionLog {
    var p: Int? = null
    var V0: List<Int>? = null
    var Vp_ext: List<Int>? = null
    var j: Long? = null
    var B: Long? = null
    var V: List<Int>? = null

    val contractionExecutionList: MutableList<ContractionVectorExecution> = mutableListOf()

    val algorithmicExecutionList: MutableList<AlgorithmicFunctionExecution> = mutableListOf()
}

class ContractionVectorExecution {
    var pi: List<Int>? = null
    var tau: List<Int>? = null
    var r: List<Int>? = null
}

class AlgorithmicFunctionExecution {
    var r: Long? = null
    var beta: Long? = null
    var gamma: Int? = null
    var V: List<Int>? = null
    var c: List<Int>? = null
    var tau: List<Int>? = null
    var D: List<Int>? = null
    var alpha: Int? = null
    var alphaStar: Int? = null
    var gammaStar: Int? = null
    var g: Long? = null
    var x: Long? = null
    var y: Long? = null
}

