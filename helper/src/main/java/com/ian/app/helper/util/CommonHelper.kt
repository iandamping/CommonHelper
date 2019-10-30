package com.ian.app.helper.util

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.ian.app.helper.R
import kotlinx.android.synthetic.main.activity_fullscreen.*
import timber.log.Timber

/**
 *
Created by Ian Damping on 25/05/2019.
Github = https://github.com/iandamping
 */

inline fun <reified T> T.logD(msg: String?) {
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


fun Context.fullScreen(imageUrl: String?) {
    imageUrl?.let {
//        val alert = Dialog(this, R.style.AppTheme)
        val alert = Dialog(this, R.style.Theme_AppCompat_Light)
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alert.setContentView(R.layout.activity_fullscreen)
        alert.setCanceledOnTouchOutside(true)
        alert.fullScreenImageView.loadWithGlide(imageUrl)
        alert.show()
        alert.ivClose.setOnClickListener {
            alert.dismiss()
        }
    }

}

fun Context.myToast(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun FragmentActivity.checkConnectivityStatus(status:(Boolean)->Unit) {
    LiveDataNetworkChangeListener(this).apply {
        this.observe(this@checkConnectivityStatus, Observer {
            status(it)
        })
    }
}

fun Fragment.checkConnectivityStatus(status:(Boolean)->Unit) {
    if (context != null) {
        LiveDataNetworkChangeListener(context!!).apply {
            this.observe(this@checkConnectivityStatus.viewLifecycleOwner, Observer {
                status(it)
            })
        }
    }
}