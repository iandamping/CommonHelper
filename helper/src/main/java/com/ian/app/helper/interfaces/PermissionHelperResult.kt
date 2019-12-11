package com.ian.app.helper.interfaces

import androidx.fragment.app.FragmentActivity

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PermissionHelperResult {

    fun getCameraReadWriteStoragePermission(activity: FragmentActivity, isGranted:(Boolean) ->Unit)

    fun getReadStoragePermission(activity: FragmentActivity, isGranted:(Boolean) ->Unit, isDenied:(Boolean) ->Unit)

    fun getWriteStoragePermission(activity: FragmentActivity, isGranted:(Boolean) ->Unit, isDenied:(Boolean) ->Unit)

    fun getCameraPermission(activity: FragmentActivity, isGranted:(Boolean) ->Unit, isDenied:(Boolean) ->Unit)
}