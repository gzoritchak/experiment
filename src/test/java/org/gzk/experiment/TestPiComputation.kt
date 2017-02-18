package org.gzk.experiment

import org.junit.Assert.assertEquals
import org.junit.Test


class TestPiComputation {

    @Test
    fun oneThreadComputation() {
        val seriesSize = 1_000_000_000L
        val pi = resultAndTime { sumLeibnizBetween(0, seriesSize -1) }
        assertEquals(Math.PI, pi.result, .000_000_01)
        println("Computation of $seriesSize elements in ${pi.ms} ms")
    }

    @Test
    fun fanOutComputation() {
        val chunkSize = 100_000L
        val chunkCount = 10_0000
        val pi = resultAndTime { fanOutSumLeibniz(chunkSize, chunkCount) }
        assertEquals(Math.PI, pi.result, .000_000_01)
        println("Computation of ${chunkCount*chunkSize} elements in ${pi.ms} ms")
    }
}

data class ResulAndTime<T>(val result: T, val ms: Long)

fun <T> resultAndTime(block: () -> T): ResulAndTime<T> {
    val start = System.currentTimeMillis()
    val result = block()
    return ResulAndTime(result, System.currentTimeMillis() - start)
}
