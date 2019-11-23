package com.ian.app.helper.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

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


fun <T, A> ssotResultLiveData(databaseQuery: () -> LiveData<T>,
                              networkCall: suspend () -> ResultToConsume<A>,
                              saveCallResult: suspend (A) -> Unit): LiveData<ResultToConsume<T>> =
        liveData(Dispatchers.IO) {
            //emit loading with cache data if exist
            val disposables = emitSource(databaseQuery.invoke().map {
                ResultToConsume.loading(it)
            })
            try {
                val responseStatus = networkCall.invoke()
                disposables.dispose()
                check(responseStatus.status == ResultToConsume.Status.SUCCESS){
                    " ${responseStatus.message} "
                }
                assert(responseStatus.data!=null){
                    " data is null "
                }
                saveCallResult(responseStatus.data!!)
                emitSource(databaseQuery.invoke().map { ResultToConsume.success(it) })
            } catch (e: Exception){
                emitSource(databaseQuery.invoke().map { ResultToConsume.error(e.message!!,it) })
            }
        }


fun <T, A> ssotFlowResultLiveData(databaseQuery: () -> Flow<T>,
                               networkCall: suspend () -> ResultToConsume<A>,
                               saveCallResult: suspend (A) -> Unit): LiveData<ResultToConsume<T>> =
    liveData(Dispatchers.IO) {
        //emit loading with cache data if exist
        val disposables = emitSource(databaseQuery.invoke().map {
            ResultToConsume.loading(it)
        }.asLiveData())

        try {
            val responseStatus = networkCall.invoke()
            disposables.dispose()
            check(responseStatus.status == ResultToConsume.Status.SUCCESS){
                " ${responseStatus.message} "
            }
            assert(responseStatus.data!=null){
                " data is null "
            }
            saveCallResult(responseStatus.data!!)
            emitSource(databaseQuery.invoke().map { ResultToConsume.success(it) }.asLiveData())
        } catch (e: Exception){
            emitSource(databaseQuery.invoke().map { ResultToConsume.error(e.message!!,it) }.asLiveData())
        }
    }