package org.gzk.experiment

import kotlinx.coroutines.experimental.*

fun fanOutSumLeibniz(chunkSize: Long, chunkCount: Int) = runBlocking {
    val deferredResults = arrayListOf<Deferred<Double>>()
    for (i in 0..chunkCount - 1) {
        deferredResults += asyncSumLeibnizBetween(i * chunkSize, (i + 1) * chunkSize - 1)
    }
    deferredResults.sumByDouble { it.await() }
}

fun asyncSumLeibnizBetween(start: Long, end: Long) = async(CommonPool) {
    sumLeibnizBetween(start, end)
}

fun sumLeibnizBetween(start: Long, end: Long) =
        4 * (start..end).sumByDouble {
            (if (it % 2 == 0L) 1.0 else -1.0) / // 1 for even, -1 for odd
                    (1 + 2 * it)
        }
