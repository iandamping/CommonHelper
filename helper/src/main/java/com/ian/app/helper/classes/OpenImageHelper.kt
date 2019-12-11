package com.ian.app.helper.classes

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.ian.app.helper.interfaces.CommonHelperResult
import com.ian.app.helper.interfaces.OpenImageHelperResult
import com.ian.app.helper.util.Constant.RequestOpenCamera
import com.ian.app.helper.util.Constant.RequestSelectGalleryImage
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.*


/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
internal class OpenImageHelper : OpenImageHelperResult, KoinComponent {
    private val saveCaptureImagePath = "picture" + System.currentTimeMillis() + ".jpeg"
    private val saveFilterImagePath = "filterImage" + System.currentTimeMillis() + ".jpeg"
    private val helper: CommonHelperResult by inject()
    private val maxWidth = 612
    private val maxHeight = 816

    override fun getBitmapFromAssets(
        ctx: Context,
        fileName: String,
        widthImage: Int,
        heightImage: Int
    ): Bitmap? {
        val assetManager: AssetManager = ctx.assets!!
        val inputStreams: InputStream
        try {
            val options: BitmapFactory.Options = BitmapFactory.Options()
            options.inJustDecodeBounds = true

            inputStreams = assetManager.open(fileName)
            //calculate sample size
            options.inSampleSize = calculateSampleSize(options, widthImage, heightImage)
            //decode bitmpat with samplesize set
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeStream(inputStreams, null, options)
        } catch (e: IOException) {
            helper.timberLogE(e.message)
        }
        return null
    }

    override fun getBitmapFromGallery(ctx: Context, path: Uri): Bitmap? {
        val filePathColum = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            ctx.applicationContext?.contentResolver?.query(path, filePathColum, null, null, null)
        cursor?.moveToFirst()

        val columnIndex: Int? = cursor?.getColumnIndex(filePathColum[0])
        val picturePath = columnIndex?.let { cursor.getString(it) }
        cursor?.close()

        val options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(picturePath, options)

        options.inSampleSize = calculateSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(picturePath, options)
    }

    override fun saveImage(views: View, bitmap: Bitmap?, packageName: String) {
        try {
            requireNotNull(bitmap) {
                "Bitmap that needs to save is null"
            }
            val pictureDirectory = Environment.getExternalStorageDirectory()
            val imageFile = File(pictureDirectory, saveFilterImagePath)
            val quality = 100
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            openImageFromSnackbar(views, imageFile, packageName)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        voidCustomMediaScannerConnection(views.context, saveFilterImagePath)
    }


    //decode File into Bitmap and compress it
    override fun decodeSampledBitmapFromFile(imageFile: File): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageFile.absolutePath, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateSampleSize(options, maxWidth, maxHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false

        var scaledBitmap = BitmapFactory.decodeFile(imageFile.absolutePath, options)

        //check the rotation of the image and display it properly
        val exif = ExifInterface(imageFile.absolutePath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
        val matrix = Matrix()
        when (orientation) {
            6 -> {
                matrix.postRotate(90F)
            }
            3 -> {
                matrix.postRotate(180F)
            }
            8 -> {
                matrix.postRotate(270F)
            }
        }
        scaledBitmap = Bitmap.createBitmap(
            scaledBitmap,
            0,
            0,
            scaledBitmap.width,
            scaledBitmap.height,
            matrix,
            true
        )
        return scaledBitmap
    }

    private fun openImageFromSnackbar(views: View, imageFile: File, packageName: String) {
        val snackbar = Snackbar
            .make(views, "Image saved to gallery!", Snackbar.LENGTH_LONG)
            .setAction("OPEN") {
                val i = Intent(Intent.ACTION_VIEW)
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                val uri =
                    FileProvider.getUriForFile(
                        views.context,
                        packageName,
                        imageFile
                    )
                i.setDataAndType(uri, "image/*")
                views.context.startActivity(i)
            }
        snackbar.show()
    }

    private fun calculateSampleSize(
        options: BitmapFactory.Options,
        requiredWidth: Int,
        requiredHeight: Int
    ): Int {
        val height = options.outHeight
        val widht = options.outWidth
        var inSampleSize = 1

        if (height > requiredHeight || widht > requiredHeight) {
            val halfHeight = height / 2
            val halfWidth = widht / 2

            while ((halfHeight / inSampleSize) >= requiredHeight && (halfWidth / inSampleSize) >= requiredWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    override fun bitmapToFile(ctx: Context, bitmap: Bitmap?): File {
        val f = File(ctx.cacheDir, "image_uploads")
        f.createNewFile()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val fos = FileOutputStream(f)
        fos.write(byteArray)
        fos.flush()
        fos.close()
        return f
    }

    override fun openImageFromGallery(fragment: Fragment) {
        val intents = Intent(Intent.ACTION_PICK)
        intents.type = "image/*"
        fragment.startActivityForResult(intents, RequestSelectGalleryImage)
    }

    override fun openImageFromGallery(activity: FragmentActivity) {
        val intents = Intent(Intent.ACTION_PICK)
        intents.type = "image/*"
        activity.startActivityForResult(intents, RequestSelectGalleryImage)
    }

    override fun openImageFromCamera(fragment: Fragment, packageName: String) {
        val pictureUri: Uri = FileProvider.getUriForFile(
            fragment.context!!,
            packageName,
            createImageFileFromPhoto(fragment.context!!)
        )
        val intents = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intents.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri)
        intents.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        fragment.startActivityForResult(intents, RequestOpenCamera)
    }

    override fun openImageFromCamera(activity: FragmentActivity, packageName: String) {
        val pictureUri: Uri = FileProvider.getUriForFile(
            activity,
            packageName,
            createImageFileFromPhoto(activity)
        )
        val intents = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intents.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri)
        intents.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        activity.startActivityForResult(intents, RequestOpenCamera)
    }

    override fun createImageFileFromPhoto(context: Context): File {
        return nonVoidCustomMediaScannerConnection(context, saveCaptureImagePath)
    }

    private fun nonVoidCustomMediaScannerConnection(ctx: Context?, paths: String): File {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val passingFile = File(directory, paths)
        MediaScannerConnection.scanFile(ctx, arrayOf(passingFile.toString()), null) { path, uri ->
            Log.i("ExternalStorage", "Scanned $path:")
            Log.i("ExternalStorage", "-> uri=$uri")
        }
        return passingFile
    }

    private fun voidCustomMediaScannerConnection(ctx: Context?, paths: String) {
        val directory = Environment.getExternalStorageDirectory()
        val passingFile = File(directory, paths)
        MediaScannerConnection.scanFile(ctx, arrayOf(passingFile.toString()), null) { path, uri ->
            Log.i("ExternalStorage", "Scanned $path:")
            Log.i("ExternalStorage", "-> uri=$uri")
        }
    }
}

