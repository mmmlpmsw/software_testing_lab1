package mmmlpmsw.testing.lab0

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round

class Function {

    private val epsilon = 1E-9

    private fun euler(n: Long, flag: Boolean = true) : Double {
        var tmp = n
        if (n == 0L)
            return 1.0
        if (flag) {
            if (tmp % 2 == 1L) return 0.0
            tmp /= 2
        }
        var res = 0.0
        for(j in 0 until tmp)
            res += bink(2 * tmp, 2 * j) * euler(j, false)
        return -res
    }

    private fun bink(n: Long, k: Long) = factorial(n)/factorial(k)/factorial(n - k)

    private fun factorial(x: Long): Long {
        return when {
            x in 0..1 -> 1
            x < 0 -> throw ArithmeticException("Value is negative.")
            else -> {
                var res = 1L
                for (i in 2 until x + 1)
                    res *= i
                res
            }
        }
    }

    fun sec(x: Double) : Double {
        if (x == Double.NaN)
            return Double.NaN
        var safeX = (x + Math.PI / 2) % Math.PI - Math.PI / 2
        if (safeX == -Math.PI) safeX = 0.0
        if (abs(safeX) == Math.PI/2)
            return Double.POSITIVE_INFINITY

        var result = 0.0
        var current = 1.0
        var i = 1
        while (abs(current) >= epsilon / 10) {
            result += current
            current = (abs(euler(2L * i)) / factorial(2L * i)) * safeX.pow(2 * i)
            i ++
        }
        val h = round((x + Math.PI/2)/Math.PI - 0.5)
        return (-1.0).pow(h) * result
    }
}



