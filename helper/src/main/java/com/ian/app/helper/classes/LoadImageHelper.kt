package com.ian.app.helper.classes

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ian.app.helper.R
import com.ian.app.helper.interfaces.LoadImageResult
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */
internal class LoadImageHelper: LoadImageResult {

   override fun ImageView.loadWithGlideSmall(url: String?) {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).override(150, 150)
        Glide.with(this.context).load(url).apply(requestOptions).placeholder(R.drawable.empty_image).thumbnail(0.25f)
            .into(this)
    }

    override fun ImageView.loadWithGlideCustomSize(url: String?,width:Int, height:Int) {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).override(width, height)
        Glide.with(this.context).load(url).apply(requestOptions).placeholder(R.drawable.empty_image).thumbnail(0.25f)
            .into(this)
    }

    override fun ImageView.loadWithGlide(url: String?) {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(this.context).load(url).apply(requestOptions).placeholder(R.drawable.empty_image).thumbnail(0.25f).into(this)
    }

    override fun ImageView.loadWithGlide(drawable:Drawable){
        Glide.with(this.context).load(drawable).into(this)
    }

    override fun ImageView.loadWithGlide(bitmap: Bitmap){
        Glide.with(this.context).load(drawable).into(this)
    }

    /*fun ImageView.loadWithGlideCornerRadius(bitmap: Bitmap,radius:Int){
        Glide.with(this.context).load(bitmap).apply(RequestOptions.bitmapTransform(RoundedCorners(radius))).into(this)
    }

    fun ImageView.loadWithGlideCornerRadius(drawable:Drawable,radius:Int){
        Glide.with(this.context).load(drawable).apply(RequestOptions.bitmapTransform(RoundedCorners(radius))).into(this)
    }

    fun ImageView.loadWithGlideCornerRadius(url: String?,radius:Int){
        Glide.with(this.context).load(url).apply(RequestOptions.bitmapTransform(RoundedCorners(radius))).into(this)
    }*/

    override fun Context.loadFullScreen(imageUrl: String?) {
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

    override fun Context.loadFullScreen(bitmap: Bitmap?) {
        bitmap?.let {
            //        val alert = Dialog(this, R.style.AppTheme)
            val alert = Dialog(this, R.style.Theme_AppCompat_Light)
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setContentView(R.layout.activity_fullscreen)
            alert.setCanceledOnTouchOutside(true)
            alert.fullScreenImageView.loadWithGlide(bitmap)
            alert.show()
            alert.ivClose.setOnClickListener {
                alert.dismiss()
            }
        }
    }
}


