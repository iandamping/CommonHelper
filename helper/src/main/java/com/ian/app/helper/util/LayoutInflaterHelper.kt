package com.ian.app.helper.util

import android.content.Context
import android.view.*
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.ian.app.helper.R
import com.ian.app.helper.interfaces.ViewHelperResult

/**
 *
Created by Ian Damping on 31/05/2019.
Github = https://github.com/iandamping
 */

inline val Context.layoutInflater: android.view.LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater

