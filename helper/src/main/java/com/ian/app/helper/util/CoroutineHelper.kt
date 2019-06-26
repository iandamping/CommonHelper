package com.ian.app.helper.util

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
            // you can log an error here and/or make a more finer-grained
            // analysis of the cause to see if retry is needed
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block() // last attempt
}



inline fun <reified T> CoroutineScope.doSomethingWithDeferred(
    deferred: Deferred<T>,
    crossinline onSuccess: (T) -> Unit,
    crossinline onFailed: (String) -> Unit
) {
    this.launch {
        try {
            onSuccess(deferred.await())
        } catch (t: Throwable) {
            onFailed(t.localizedMessage)
        }
    }
}

inline fun <reified T, U> CoroutineScope.deferredPair(
    deferredSource1: Pair<Deferred<T>, Deferred<U>>,
    crossinline onSuccess: (T, U) -> Unit,
    crossinline onFailed: (String) -> Unit
) {
    this.launch {
        try {
            onSuccess(deferredSource1.first.await(), deferredSource1.second.await())
        } catch (t: Throwable) {
            onFailed(t.localizedMessage)
        }
    }
}

inline fun <reified A, X, E> CoroutineScope.deferredTriple(
    deferredSource: Triple<Deferred<A>, Deferred<X>, Deferred<E>>,
    crossinline onSuccess: (A, X, E) -> Unit,
    crossinline onFailed: (String) -> Unit
) {
    this.launch {
        try {
            onSuccess(deferredSource.first.await(), deferredSource.second.await(), deferredSource.third.await())
        } catch (t: Throwable) {
            onFailed(t.localizedMessage)
        }
    }
}

inline fun <reified T, U, S, K> CoroutineScope.combineTripleWithSingleDeferred(
    deferredSource1: Pair<Triple<Deferred<T>, Deferred<U>, Deferred<S>>, Deferred<K>>,
    crossinline onSuccess: (Pair<Triple<T, U, S>, K>) -> Unit,
    crossinline onFailed: (String) -> Unit
) {
    this.launch {
        try {
            onSuccess(
                Pair(
                    Triple(
                        deferredSource1.first.first.await(),
                        deferredSource1.first.second.await(),
                        deferredSource1.first.third.await()
                    ),
                    deferredSource1.second.await()
                )
            )
        } catch (t: Throwable) {
            onFailed(t.localizedMessage)
        }
    }
}

inline fun <reified L, Y, N, D, A> CoroutineScope.combineTripleWithPairDeferred(
    deferredSource1: Pair<Triple<Deferred<L>, Deferred<Y>, Deferred<N>>, Pair<Deferred<D>, Deferred<A>>>,
    crossinline onSuccess: (Pair<Triple<L, Y, N>, Pair<D, A>>) -> Unit,
    crossinline onFailed: (String) -> Unit
) {
    this.launch {
        try {
            onSuccess(
                Pair(
                    Triple(
                        deferredSource1.first.first.await(),
                        deferredSource1.first.second.await(),
                        deferredSource1.first.third.await()
                    ),
                    Pair(
                        deferredSource1.second.first.await(),
                        deferredSource1.second.second.await()
                    )

                )
            )
        } catch (t: Throwable) {
            onFailed(t.localizedMessage)
        }
    }
}

inline fun <reified R, O, D, I, T, H> CoroutineScope.combineTripleWithTripleDeferred(
    deferredSource1: Pair<Triple<Deferred<R>, Deferred<O>, Deferred<D>>, Triple<Deferred<I>, Deferred<T>, Deferred<H>>>,
    crossinline onSuccess: (Pair<Triple<R, O, D>, Triple<I, T, H>>) -> Unit,
    crossinline onFailed: (String) -> Unit
) {
    this.launch {
        try {
            onSuccess(
                Pair(
                    Triple(
                        deferredSource1.first.first.await(),
                        deferredSource1.first.second.await(),
                        deferredSource1.first.third.await()
                    ),
                    Triple(
                        deferredSource1.second.first.await(),
                        deferredSource1.second.second.await(),
                        deferredSource1.second.third.await()
                    )

                )
            )
        } catch (t: Throwable) {
            onFailed(t.localizedMessage)
        }
    }
}

inline fun <reified T, U, S, K> CoroutineScope.combinePairWithPairDeferred(
    deferredSource1: Pair<Pair<Deferred<T>, Deferred<U>>, Pair<Deferred<S>, Deferred<K>>>,
    crossinline onSuccess: (Pair<Pair<T, U>, Pair<S, K>>) -> Unit,
    crossinline onFailed: (String) -> Unit
) {
    this.launch {
        try {
            onSuccess(
                Pair(
                    Pair(deferredSource1.first.first.await(), deferredSource1.first.second.await()),
                    Pair(deferredSource1.second.first.await(), deferredSource1.second.second.await())
                )
            )
        } catch (t: Throwable) {
            onFailed(t.localizedMessage)
        }
    }
}
