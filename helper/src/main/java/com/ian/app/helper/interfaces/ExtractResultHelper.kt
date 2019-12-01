package com.ian.app.helper.interfaces

import androidx.lifecycle.LiveData
import com.ian.app.helper.data.ResultToConsume
import kotlinx.coroutines.flow.Flow

interface ExtractResultHelper {

    fun <T, A> ssotLiveDataResult(
        databaseQuery: () -> Flow<T>, networkCall: suspend () -> ResultToConsume<A>,
        saveCallResult: suspend (A) -> Unit
    ): LiveData<ResultToConsume<T>>

    fun <T, A> ssotLiveDatasResult(
        databaseQuery: () -> LiveData<T>, networkCall: suspend () -> ResultToConsume<A>,
        saveCallResult: suspend (A) -> Unit
    ): LiveData<ResultToConsume<T>>

    fun <T, A> ssotFlowResult(
        databaseQuery: () -> Flow<T>, networkCall: suspend () -> ResultToConsume<A>,
        saveCallResult: suspend (A) -> Unit
    ): Flow<ResultToConsume<T>>

    fun <T, A> ssotSearchLiveDataResult(
        data: String, databaseQuery: () -> LiveData<T>,
        networkCall: suspend (querry: String) -> ResultToConsume<A>,
        saveCallResult: suspend (A) -> Unit
    ): LiveData<ResultToConsume<T>>

    fun <T> searchLiveDataResult(
        data: String,
        networkCall: suspend (querry: String) -> ResultToConsume<T>
    ): LiveData<ResultToConsume<T>>

    fun <T> remoteLiveDataResult(networkCall: suspend () -> ResultToConsume<T>): LiveData<ResultToConsume<T>>

    fun <T> remoteFlowResult(networkCall: suspend () -> ResultToConsume<T>): Flow<ResultToConsume<T>>
}