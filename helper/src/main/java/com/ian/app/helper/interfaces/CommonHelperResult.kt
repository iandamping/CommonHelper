package com.ian.app.helper.interfaces

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


/**
 * Created by Ian Damping on 01,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface CommonHelperResult {

    fun Context.myToast(msg: String?)

    fun FragmentActivity.checkConnectivityStatus(status: (Boolean) -> Unit)

    fun Fragment.checkConnectivityStatus(status: (Boolean) -> Unit)

    fun timberLogE(msg: String?)

    fun timberLogD(msg: String?)

    fun timberLogI(msg: String?)

}