package com.ian.app.commonhelper

import android.app.Application
import com.ian.app.helper.di.timberModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/**
 *
Created by Ian Damping on 04/10/2019.
Github = https://github.com/iandamping
 */
class HelperApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HelperApp)
            modules(timberModule)
        }
    }
}