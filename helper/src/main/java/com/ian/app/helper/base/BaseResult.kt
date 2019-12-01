package com.ian.app.helper.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.ian.app.helper.data.ResultToConsume
import com.ian.app.helper.interfaces.ExtractResultHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*


/**
 *
Created by Ian Damping on 19/09/2019.
Github = https://github.com/iandamping

The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.Status.SUCCESS] - with data from database
 * [Result.Status.ERROR] - if error has occurred from any source
 * [Result.Status.LOADING]
 *
 */
internal class BaseResult : ExtractResultHelper {

    private fun searchKeywordFlow(data: String): Flow<String> = flow {
        if (data != "") emit(data)
    }

    override fun <T, A> ssotLiveDataResult(
        databaseQuery: () -> Flow<T>,
        networkCall: suspend () -> ResultToConsume<A>,
        saveCallResult: suspend (A) -> Unit
    ): LiveData<ResultToConsume<T>> {
        return liveData(Dispatchers.IO) {
            //emit loading
            val disposables = emitSource(databaseQuery.invoke().map {
                ResultToConsume.loading(it)
            }.asLiveData())

            try {
                val responseStatus = networkCall.invoke()
                disposables.dispose()
                check(responseStatus.status == ResultToConsume.Status.SUCCESS) {
                    " ${responseStatus.message} "
                }
                assert(responseStatus.data != null) {
                    " data is null "
                }
                saveCallResult(responseStatus.data!!)
                emitSource(databaseQuery.invoke().map { ResultToConsume.success(it) }.asLiveData())
            } catch (e: Exception) {
                emitSource(databaseQuery.invoke().map {
                    ResultToConsume.error(e.message!!, it)
                }.asLiveData())
            }
        }
    }

    override fun <T, A> ssotLiveDatasResult(
        databaseQuery: () -> LiveData<T>,
        networkCall: suspend () -> ResultToConsume<A>,
        saveCallResult: suspend (A) -> Unit
    ): LiveData<ResultToConsume<T>> {
        return liveData(Dispatchers.IO) {
            //emit loading with cache data if exist
            val disposables = emitSource(databaseQuery.invoke().map {
                ResultToConsume.loading(it)
            })
            try {
                val responseStatus = networkCall.invoke()
                disposables.dispose()
                check(responseStatus.status == ResultToConsume.Status.SUCCESS) {
                    " ${responseStatus.message} "
                }
                assert(responseStatus.data != null) {
                    " data is null "
                }
                saveCallResult(responseStatus.data!!)
                emitSource(databaseQuery.invoke().map { ResultToConsume.success(it) })
            } catch (e: java.lang.Exception) {
                emitSource(databaseQuery.invoke().map { ResultToConsume.error(e.message!!, it) })
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun <T, A> ssotFlowResult(
        databaseQuery: () -> Flow<T>,
        networkCall: suspend () -> ResultToConsume<A>,
        saveCallResult: suspend (A) -> Unit
    ): Flow<ResultToConsume<T>> {
        return flow {
            // emit loading
            emit(ResultToConsume.loading())

            // map database source into an object
            val source = databaseQuery.invoke().map {
                ResultToConsume.success(it)
            }

            // variable that invoked networkCall function
            val responseStatus = networkCall.invoke()
            when {
                responseStatus.data != null -> {
                    if (responseStatus.status == ResultToConsume.Status.SUCCESS) {
                        saveCallResult(responseStatus.data)
                    } else if (responseStatus.status == ResultToConsume.Status.ERROR) {
                        emit(ResultToConsume.error(responseStatus.message!!))
                    }
                }
                else -> {
                    if (responseStatus.message != null) {
                        emit(ResultToConsume.error(responseStatus.message))
                    } else {
                        emit(ResultToConsume.error("Unable to resolve host of your API"))
                    }
                    // emit database object into flow
                    emitAll(source)
                }
            }
            // emit database object into flow
            emitAll(source)
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun <T, A> ssotSearchLiveDataResult(
        data: String,
        databaseQuery: () -> LiveData<T>,
        networkCall: suspend (querry: String) -> ResultToConsume<A>,
        saveCallResult: suspend (A) -> Unit
    ): LiveData<ResultToConsume<T>> = liveData {
        //emit loading with cache data if exist
        val disposables = emitSource(databaseQuery.invoke().map {
            ResultToConsume.loading(it)
        })

        try {
            searchKeywordFlow(data).debounce(200L).buffer().map { networkCall.invoke(it) }
                .distinctUntilChanged()
                .collectLatest { resultFlow ->
                    disposables.dispose()
                    check(resultFlow.status == ResultToConsume.Status.SUCCESS) {
                        " result status is not success "
                    }
                    assert(resultFlow.data != null) {
                        " data from result is null"
                    }
                    saveCallResult(resultFlow.data!!)
                    emitSource(databaseQuery.invoke().map { ResultToConsume.success(it) })
                }
        } catch (e: Exception) {
            emitSource(databaseQuery.invoke().map { ResultToConsume.error(e.message!!, it) })
        }
    }

    override fun <T> searchLiveDataResult(
        data: String,
        networkCall: suspend (querry: String) -> ResultToConsume<T>
    ): LiveData<ResultToConsume<T>> = liveData {
        emit(ResultToConsume.loading())
        try {
            searchKeywordFlow(data).debounce(200L).buffer().map { networkCall.invoke(it) }
                .distinctUntilChanged()
                .collectLatest { resultFlow ->
                    check(resultFlow.status == ResultToConsume.Status.SUCCESS) {
                        " result status is not success "
                    }
                    assert(resultFlow.data != null) {
                        " data from result is null"
                    }
                    emit(ResultToConsume.success(resultFlow.data!!))
                }
        } catch (e: Exception) {
            emit(ResultToConsume.error(e.message!!))
        }

    }

    override fun <T> remoteLiveDataResult(networkCall: suspend () -> ResultToConsume<T>): LiveData<ResultToConsume<T>> =
        liveData {
            emit(ResultToConsume.loading())
            val responseStatus = networkCall.invoke()
            when {
                responseStatus.data != null -> {
                    if (responseStatus.status == ResultToConsume.Status.SUCCESS) {
                        emit(ResultToConsume.success(responseStatus.data))
                    } else if (responseStatus.status == ResultToConsume.Status.ERROR) {
                        emit(ResultToConsume.error(networkCall.invoke().message!!))
                    }
                }
                else -> {
                    if (responseStatus.message != null) {
                        emit(ResultToConsume.error(responseStatus.message))
                    } else emit(ResultToConsume.error("Unable to resolve host of your API"))
                }
            }
        }

    override fun <T> remoteFlowResult(networkCall: suspend () -> ResultToConsume<T>): Flow<ResultToConsume<T>> {
        return flow {
            emit(ResultToConsume.loading())
            val responseStatus = networkCall.invoke()
            when {
                responseStatus.data != null -> {
                    if (responseStatus.status == ResultToConsume.Status.SUCCESS) {
                        emit(ResultToConsume.success(responseStatus.data))
                    } else if (responseStatus.status == ResultToConsume.Status.ERROR) {
                        emit(ResultToConsume.error(networkCall.invoke().message!!))
                    }
                }
                else -> {
                    if (responseStatus.message != null) {
                        emit(ResultToConsume.error(responseStatus.message))
                    } else emit(ResultToConsume.error("Unable to resolve host of your API"))
                }
            }
        }
    }

}

