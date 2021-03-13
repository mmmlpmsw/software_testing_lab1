package mmmlpmsw.testing.lab0

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import kotlin.math.cos

class FunctionTest {
    private val f = Function()

    @Test
    fun testLocalExtremum() {
        assertEquals(-1.0, f.sec(Math.PI))
        assertEquals(-1.0, f.sec(-Math.PI))
        assertEquals(1.0, f.sec(0.0))
        assertEquals(1.0, f.sec(2 * Math.PI))
    }

    @Test
    fun testAsymptotes() {
        assertEquals(Double.POSITIVE_INFINITY, f.sec(Math.PI/2))
        assertEquals(Double.POSITIVE_INFINITY, f.sec(-Math.PI/2))
    }

    @Test
    fun testYIntercept() {
        assertEquals(1 / cos(0.0), f.sec(0.0))
    }

    @Test
    fun testInfinitiesAsBorders() {
        assertEquals(Double.NaN, f.sec(Double.POSITIVE_INFINITY))
        assertEquals(Double.NaN, f.sec(Double.NEGATIVE_INFINITY))
    }

//    @Test
//    fun testLeftPartOfTheSegment() {
//        assertEquals(1 / cos(-Math.PI / 4), f.sec(Math.PI / 4), 0.001)
//    }
//
//    @Test
//    fun testRightPartOfTheSegment() {
//        assertEquals(1 / cos(Math.PI / 3), f.sec(Math.PI / 3), 0.001)
//        println(f.sec(Double.POSITIVE_INFINITY))
//    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/sec-test.csv"])
    fun parameterizedTest (argument: Double, expectedResult: Double) {
        assertEquals(expectedResult, f.sec(argument), 0.001)
    }

    @Test
    fun testNanArgument() {
        assertEquals(Double.NaN, f.sec(Double.NaN))
    }

}