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

fun logD(msg: String?) {
    Timber.tag("#### timber logger ####").d(msg)
   /* val tag = T::class.java.simpleName
    Log.d(tag, msg)*/
}

fun logE(msg: String?) {
    Timber.tag("#### timber logger ####").e(msg)
   /* val tag = T::class.java.simpleName
    Log.e(tag, msg)*/
}

 fun logI(msg: String?) {
    Timber.tag("#### timber logger ####").i(msg)
    /* val tag = T::class.java.simpleName
     Log.e(tag, msg)*/
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