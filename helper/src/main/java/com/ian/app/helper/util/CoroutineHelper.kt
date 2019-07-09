package com.ian.app.helper.util

import android.util.Log
import com.ian.app.helper.BuildConfig
import com.ian.app.helper.util.Constant.factor
import com.ian.app.helper.util.Constant.initialDelay
import com.ian.app.helper.util.Constant.maxDelay
import com.ian.app.helper.util.Constant.times
import kotlinx.coroutines.*
import java.io.IOException

/**
 *
Created by Ian Damping on 27/05/2019.
Github = https://github.com/iandamping
 */

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

suspend fun <T> retryIO(block: suspend () -> T): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) Log.e("Retry Io", e.localizedMessage)
            // you can log an error here and/or make a more finer-grained
            // analysis of the cause to see if retry is needed
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block() // last attempt
}

/*New method for retrying network calls
* how to use this ?
*  uiScope.extractDeferred {
            vm.getPopularTvAsync().apply {
                _data.value = this.await().results
            }
        }
* and JUST like that, our network calls now support retry
*/

inline fun <T> CoroutineScope.extractDeferred(crossinline heavyFunction: suspend () -> Deferred<T>) {
    this.launch {
        retryIO { heavyFunction().await() }
    }
}


inline fun <reified T, U> CoroutineScope.extractDeferredPair(noinline heavyFunction: suspend () -> Pair<Deferred<T>, Deferred<U>>) {
    this.launch {
        retryIO {
            Pair(heavyFunction.invoke().first.await(), heavyFunction.invoke().second.await())
        }
    }
}

inline fun <reified A, X, E> CoroutineScope.extractDeferredTriple(noinline heavyFunction: suspend () -> Triple<Deferred<A>, Deferred<X>, Deferred<E>>) {
    this.launch {
        retryIO {
            Triple(
                heavyFunction.invoke().first.await(),
                heavyFunction.invoke().second.await(),
                heavyFunction.invoke().third.await()
            )
        }
    }
}

inline fun <reified T, U, S, K> CoroutineScope.extractDeferredTripleWithSingle(noinline heavyFunction: suspend () -> Pair<Triple<Deferred<T>, Deferred<U>, Deferred<S>>, Deferred<K>>) {
    this.launch {
        retryIO {
            Pair(
                Triple(
                    heavyFunction.invoke().first.first.await(),
                    heavyFunction.invoke().first.second.await(),
                    heavyFunction.invoke().first.third.await()
                ),
                heavyFunction.invoke().second.await()
            )
        }
    }
}

inline fun <reified L, Y, N, D, A> CoroutineScope.extractDeferredTripleWithPair(noinline heavyFunction: suspend () -> Pair<Triple<Deferred<L>, Deferred<Y>, Deferred<N>>, Pair<Deferred<D>, Deferred<A>>>) {
    this.launch {
        retryIO {
            Pair(
                Triple(
                    heavyFunction.invoke().first.first.await(),
                    heavyFunction.invoke().first.second.await(),
                    heavyFunction.invoke().first.third.await()
                ),
                Pair(
                    heavyFunction.invoke().second.first.await(),
                    heavyFunction.invoke().second.second.await()
                )

            )
        }
    }
}

inline fun <reified R, O, D, I, T, H> CoroutineScope.extractDeferredTripleWithTriple(noinline heavyFunction: suspend () -> Pair<Triple<Deferred<R>, Deferred<O>, Deferred<D>>, Triple<Deferred<I>, Deferred<T>, Deferred<H>>>) {
    this.launch {
        retryIO {
            Pair(
                Triple(
                    heavyFunction.invoke().first.first.await(),
                    heavyFunction.invoke().first.second.await(),
                    heavyFunction.invoke().first.third.await()
                ),
                Triple(
                    heavyFunction.invoke().second.first.await(),
                    heavyFunction.invoke().second.second.await(),
                    heavyFunction.invoke().second.third.await()
                )

            )
        }

    }
}

inline fun <reified T, U, S, K> CoroutineScope.extractDeferredPairWithPair(noinline heavyFunction: suspend () -> Pair<Pair<Deferred<T>, Deferred<U>>, Pair<Deferred<S>, Deferred<K>>>) {
    this.launch {
        retryIO {
            Pair(
                Pair(heavyFunction.invoke().first.first.await(), heavyFunction.invoke().first.second.await()),
                Pair(heavyFunction.invoke().second.first.await(), heavyFunction.invoke().second.second.await())
            )
        }
    }
}
