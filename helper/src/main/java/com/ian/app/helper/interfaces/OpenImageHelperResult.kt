package com.ian.app.helper.interfaces

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.io.File

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface OpenImageHelperResult {

    fun getBitmapFromAssets(
        ctx: Context,
        fileName: String,
        widthImage: Int,
        heightImage: Int
    ): Bitmap?

    fun openImageFromGallery(fragment: Fragment)

    fun openImageFromGallery(activity: FragmentActivity)

    fun openImageFromCamera(fragment: Fragment, packageName: String)

    fun openImageFromCamera(activity: FragmentActivity, packageName: String)

    fun getBitmapFromGallery(ctx: Context, path: Uri): Bitmap?

    fun saveImage(views: View, bitmap: Bitmap?, packageName: String)

    fun decodeSampledBitmapFromFile(imageFile: File): Bitmap

    fun bitmapToFile(ctx: Context, bitmap: Bitmap?): File

    fun createImageFileFromPhoto(context: Context): File
}