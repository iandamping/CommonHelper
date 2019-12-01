package com.ian.app.helper.interfaces

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentActivity


/**
 * Created by Ian Damping on 01,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ViewHelperResult {

    fun FragmentActivity.fullScreenAnimation()

    fun ViewGroup.inflates(layout: Int): View

    fun View.visible()

    fun View.gone()

    fun EditText.requestError(message: String?)
}