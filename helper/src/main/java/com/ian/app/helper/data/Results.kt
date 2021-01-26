package com.ian.app.helper.data


/**
 * Created by Ian Damping on 18,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
sealed class Results<out R> {
    data class Success<out T>(val data: T) : Results<T>()
    object Loading : Results<Nothing>()
    data class Error(val exception: Exception) : Results<Nothing>()
}