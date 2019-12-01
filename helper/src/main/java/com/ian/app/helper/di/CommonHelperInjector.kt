package com.ian.app.helper.di

import com.ian.app.helper.classes.CommonHelper
import com.ian.app.helper.interfaces.LoadImageResult
import com.ian.app.helper.interfaces.ViewHelperResult
import com.ian.app.helper.classes.LoadImageHelper
import com.ian.app.helper.classes.ViewHelper
import com.ian.app.helper.interfaces.CommonHelperResult
import org.koin.dsl.module


/**
 * Created by Ian Damping on 01,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
val commonHelperModule = module {
    factory { ViewHelper() as ViewHelperResult }
    factory { LoadImageHelper() as LoadImageResult }
    factory { CommonHelper() as CommonHelperResult }
}