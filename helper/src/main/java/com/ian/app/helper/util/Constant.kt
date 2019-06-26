package com.ian.app.helper.util

/**
 *
Created by Ian Damping on 25/05/2019.
Github = https://github.com/iandamping
 */
object Constant {
    const val succesWork = "Success"
    const val failedWork = "Failed"

    /*For Coroutine Helper RetryIO*/
    const val times: Int = Int.MAX_VALUE
    const val initialDelay: Long = 100 // 0.1 second
    const val maxDelay: Long = 1000    // 1 second
    const val factor: Double = 2.0
}