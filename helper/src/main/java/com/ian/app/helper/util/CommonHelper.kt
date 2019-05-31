package com.ian.app.helper.util

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.ian.app.helper.R
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 *
Created by Ian Damping on 25/05/2019.
Github = https://github.com/iandamping
 */

inline fun <reified T> T.logD(msg: String?) {
    val tag = T::class.java.simpleName
    Log.d(tag, msg)
}

inline fun <reified T> T.logE(msg: String?) {
    val tag = T::class.java.simpleName
    Log.e(tag, msg)
}

fun Context.fullScreen(imageUrl: String?) {
    imageUrl?.let {
        val alert = Dialog(this, R.style.AppTheme)
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alert.setContentView(R.layout.activity_fullscreen)
        alert.setCanceledOnTouchOutside(true)
        alert.fullScreenImageView.loadUrl(imageUrl)
        alert.show()
        alert.ivClose.setOnClickListener {
            alert.dismiss()
        }
    }

}

fun Context.myToast(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}