package com.ian.app.helper.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.ian.app.helper.R
import com.squareup.picasso.Picasso

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

fun ImageView.loadUrl(url: String?) {
    url?.let { Picasso.get().load(it).placeholder(R.drawable.empty_image).into(this) }

}

fun ImageView.loadUrlResize(url: String?) {
    url?.let { Picasso.get().load(it).placeholder(R.drawable.empty_image).resize(100, 100).into(this) }

}

fun ImageView.loadResizeWithGlide(url:String?, context: Context){
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(150,150)
    Glide.with(context).load(url).apply(requestOptions).placeholder(R.drawable.empty_image).thumbnail(0.25f).into(this)
}

fun ImageView.loadWithGlide(url:String?, context: Context){
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(context).load(url).apply(requestOptions).placeholder(R.drawable.empty_image).thumbnail(0.25f).into(this)
}

