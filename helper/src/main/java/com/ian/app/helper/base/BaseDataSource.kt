package com.ian.app.helper.base

import com.ian.app.helper.data.ResultToConsume
import com.ian.app.helper.util.timberLogE
import retrofit2.Response

abstract class BaseDataSource {

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

    private fun <T> error(message: String): ResultToConsume<T> {
        timberLogE(message)
        return ResultToConsume.error("Network call has failed for a following reason: $message")
    }

}