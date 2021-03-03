package mmmlpmsw.testing.lab0

import kotlin.math.abs
import kotlin.math.pow

class SecCosFunction {
    val limitValue = 1.0e8
    fun sec(x : Double): Double {
        var xn = 1.0
        var prevSum = 0.0
        var sum = 1.0
        val EPS = 1e-10

        var n = 0
        while (abs(sum - prevSum) > EPS) {
            prevSum = sum
            xn *= (-1.0 * x.pow(2) / (2 * n + 2) / (2 * n + 1))
            sum += xn
            n++
        }
        return if (abs(1/sum) > limitValue) Double.POSITIVE_INFINITY else 1/sum
    }
}

fun main(){
    val f = SecCosFunction()
    println(f.sec(-0.5))
}