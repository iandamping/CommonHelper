package com.ian.app.helper.di

import com.ian.app.helper.base.BaseResult
import com.ian.app.helper.interfaces.ExtractResultHelper
import org.koin.dsl.module


/**
 * Created by Ian Damping on 01,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */

val baseResultModule = module {
    factory { BaseResult() as ExtractResultHelper }
}
