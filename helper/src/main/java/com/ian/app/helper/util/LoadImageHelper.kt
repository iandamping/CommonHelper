package com.ian.app.helper.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ian.app.helper.R

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

fun ImageView.loadResizeWithGlide(url: String?) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(150, 150)
    Glide.with(this.context).load(url).apply(requestOptions).placeholder(R.drawable.empty_image).thumbnail(0.25f)
        .into(this)
}

fun ImageView.loadWithGlide(url: String?) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(this.context).load(url).apply(requestOptions).placeholder(R.drawable.empty_image).thumbnail(0.25f)
        .into(this)
}

