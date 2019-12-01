package com.ian.app.helper.util

import android.util.Log
import timber.log.Timber

/**
 *
Created by Ian Damping on 25/05/2019.
Github = https://github.com/iandamping
 */

inline fun<reified T> T.logD(msg: String?) {
    val tag = T::class.java.simpleName
    if(msg!=null)Log.d(tag, msg)
}

inline fun <reified T> T.logE(msg: String?) {
    val tag = T::class.java.simpleName
    if(msg!=null)Log.e(tag, msg)
}

inline fun <reified T> T.logI(msg: String?) {
    val tag = T::class.java.simpleName
    if(msg!=null) Log.i(tag, msg)
}

inline fun <reified T> T.timberLogE(msg: String?) {
    val tag = T::class.java.simpleName
    Timber.tag("### Log in $tag class ###").e(msg)
}

inline fun <reified T> T.timberLogD(msg: String?) {
    val tag = T::class.java.simpleName
    Timber.tag("### Log in $tag class ###").d(msg)
}

inline fun <reified T> T.timberLogI(msg: String?) {
    val tag = T::class.java.simpleName
    Timber.tag("### Log in $tag class ###").i(msg)
}

