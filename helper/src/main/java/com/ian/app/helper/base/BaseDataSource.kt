package com.ian.app.helper.base

import com.ian.app.helper.data.ResultToConsume
import com.ian.app.helper.data.Results
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

abstract class BaseDataSource {

    protected inline fun <T> doOneShots(call: () -> Response<T>): Results<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Results.Success(
                    body
                )
            }
            return Results.Error(Exception())
        } catch (e: Exception) {
            return Results.Error(e)
        }
    }


    protected fun <T> Call<T>.doOneShots(data:(Results<T>) -> Unit) {
        //you must handle this with try catch
        this.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                data.invoke(Results.Error(Exception(message = "Network call has failed for a following reason: ", cause = t)))
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                try {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) data.invoke(Results.Success(body))
                    }
                } catch (e: Exception) {
                    Results.Error(Exception(message = "Network call has failed for a following reason: ", cause = e.cause))
                }
            }
        })
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "removing enum clasees with data classes")
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ResultToConsume<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ResultToConsume.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }


    @Deprecated(level = DeprecationLevel.ERROR, message = "when on failure call the callback wont sent why its error")
    protected suspend fun <T> Call<T>.doOneShot(): ResultToConsume<T> =
        //you must handle this with try catch
        suspendCancellableCoroutine { cancellableContinuation ->
            this.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    cancellableContinuation.resume(error(t.message ?: t.toString()))
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    try {
                        if (response.isSuccessful) {
                            val body = response.body()
                            if (body != null) cancellableContinuation.resume(
                                ResultToConsume.success(body)
                            )
                        }
                    } catch (e: Exception) {
                        cancellableContinuation.resume(error(e.message ?: e.toString()))
                    }
                }
            })
            cancellableContinuation.invokeOnCancellation {
                this.cancel()
            }
        }


    @Deprecated(level = DeprecationLevel.ERROR, message = "when on failure call the callback wont sent why its error")
    protected suspend fun <T> Response<T>.doOneShot(): ResultToConsume<T> = suspendCancellableCoroutine { cancellableContinuation ->
        //you must handle this with try catch
            if (this.isSuccessful) {
                val body = this.body()
                if (body != null) cancellableContinuation.resume(ResultToConsume.success(body))
            }else cancellableContinuation.resume(error(" ${this.code()} ${this.message()}"))

            cancellableContinuation.invokeOnCancellation {
                cancellableContinuation.resume(error(it?.message ?: it.toString()))
            }
        }


    private fun <T> error(message: String): ResultToConsume<T> {
        return ResultToConsume.error("Network call has failed for a following reason: $message")
    }

}