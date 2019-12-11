package com.ian.app.helper.classes


import androidx.fragment.app.FragmentActivity
import com.ian.app.helper.interfaces.PermissionHelperResult
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
internal class PermissionHelper : PermissionHelperResult {

    override fun getCameraReadWriteStoragePermission(
        activity: FragmentActivity,
        isGranted: (Boolean) -> Unit
    ) {
        Dexter.withActivity(activity).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report?.areAllPermissionsGranted()!!) {
                    isGranted(report.areAllPermissionsGranted())
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
            }

        }).check()
    }

    override fun getReadStoragePermission(
        activity: FragmentActivity,
        isGranted: (Boolean) -> Unit
        , isDenied: (Boolean) -> Unit
    ) {
        Dexter.withActivity(activity).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
               isGranted(true)
            }

            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest?,
                token: PermissionToken?
            ) {
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                isDenied(true)
            }

        }).check()
    }

    override fun getWriteStoragePermission(
        activity: FragmentActivity,
        isGranted: (Boolean) -> Unit,
        isDenied: (Boolean) -> Unit
    ) {
        Dexter.withActivity(activity).withPermission(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                isGranted(true)
            }

            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest?,
                token: PermissionToken?
            ) {
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                isDenied(true)
            }

        }).check()
    }

    override fun getCameraPermission(
        activity: FragmentActivity,
        isGranted: (Boolean) -> Unit,
        isDenied: (Boolean) -> Unit
    ) {
        Dexter.withActivity(activity).withPermission(
            android.Manifest.permission.CAMERA
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                isGranted(true)
            }

            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest?,
                token: PermissionToken?
            ) {
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                isDenied(true)
            }

        }).check()
    }
}