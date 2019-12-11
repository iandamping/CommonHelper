package com.ian.app.helper.interfaces

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView


/**
 * Created by Ian Damping on 01,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface LoadImageResult {
    fun ImageView.loadWithGlideSmall(url: String?)
    fun ImageView.loadWithGlideCustomSize(url: String?,width:Int, height:Int)
    fun ImageView.loadWithGlide(url: String?)
    fun ImageView.loadWithGlide(drawable: Drawable)
    fun ImageView.loadWithGlide(bitmap: Bitmap)
    fun Context.loadFullScreen(imageUrl: String?)
    fun Context.loadFullScreen(bitmap: Bitmap?)
}