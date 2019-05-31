package com.ian.app.helper.util

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
Created by Ian Damping on 31/05/2019.
Github = https://github.com/iandamping
 */
inline fun <reified T> Call<T>.executes(crossinline onFailure: (Throwable) -> Unit, crossinline onResponse: (T?) -> Unit) {
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            onResponse(response.body())
        }

    })
}