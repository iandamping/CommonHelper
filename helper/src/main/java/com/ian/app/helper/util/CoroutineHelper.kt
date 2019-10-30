package com.ian.app.helper.util

import com.ian.app.helper.BuildConfig
import com.ian.app.helper.util.Constant.factor
import com.ian.app.helper.util.Constant.initialDelay
import com.ian.app.helper.util.Constant.maxDelay
import com.ian.app.helper.util.Constant.times
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.IOException

/**
 *
Created by Ian Damping on 27/05/2019.
Github = https://github.com/iandamping
 */
private fun timberLogE(msg: String?) {
    Timber.tag("#### timber logger ####").e(msg)
}

inline fun CoroutineScope.doSomethingWithIOScope(crossinline heavyFunction: suspend () -> Unit?) {
    this.launch {
        withContext(Dispatchers.IO) {
            heavyFunction()
        }
    }
}

/*How to use this ?
* this retryIo need coroutine scope because it's suspend function, if it to work on Mainthread dont forget using ui Dispatchers
* simple yet powerfull
*  uiScope.launch {
            retryIO {
                liveDataState.value = OnGetData(retryIO { api.getPopularMovieAsync(MovieConstant.api_key).await() })
                liveDataState.value = OnSuccessGetData(true)
            }
        }
* */

suspend fun <T> retryIOWithReturn(block: suspend () -> T): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) timberLogE("Retry Io ${e.localizedMessage}")
            // you can log an error here and/or make a more finer-grained
            // analysis of the cause to see if retry is needed
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block() // last attempt
}


/*How to use this ?
* this retryIo need coroutine scope because it's suspend function, if it to work on Mainthread dont forget using ui Dispatchers
* simple yet powerfull
*  uiScope.launch {
            retryIO {
                yourFunction()
            }
        }
* */


suspend fun retryIO(block: suspend () -> Unit) {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) timberLogE("Retry Io ${e.localizedMessage}")
            // you can log an error here and/or make a more finer-grained
            // analysis of the cause to see if retry is needed
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block() // last attempt
}

/*Single call with retryIO*/
inline fun <T> CoroutineScope.extractDeferred(crossinline heavyFunction: suspend () -> Deferred<T>) {
    this.launch {
        retryIO { heavyFunction().await() }
    }
}

/*This serial of function is used for concurrent call*/
fun <A, B> computeDoubleResult(await1: A, await2: B): Pair<A, B> {
    return Pair(await1, await2)
}

fun <A, M, I> computeTripleResult(await1: A, await2: M, await3: I): Triple<A, M, I> {
    return Triple(await1, await2, await3)
}

fun <N, O, V, I> computeQuadResult(await1: N, await2: O,await3: V, await4: I): Pair<Pair<N,O>, Pair<V,I>> {
    return Pair(Pair(await1,await2), Pair(await3,await4))
}

fun <L, Y, N, D, A> computeQuintResult(await1: L, await2: Y,await3: N, await4: D,await5:A): Pair<Triple<L,Y,N>, Pair<D,A>> {
    return Pair(Triple(await1,await2,await3), Pair(await4,await5))
}

fun < R, O, D, I, T, H> computeSextResult(await1: R, await2: O,await3: D, await4: I,await5:T,await6:H): Pair<Triple<R,O,D>, Triple<I,T,H>> {
    return Pair(Triple(await1,await2,await3), Triple(await4,await5,await6))
}
