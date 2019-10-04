package com.ian.app.helper.di

import android.app.Application
import org.koin.dsl.module
import timber.log.Timber



/**
 *
Created by Ian Damping on 04/10/2019.
Github = https://github.com/iandamping
 */

val timberModule = module {
    single { injectTimber() }
}

private fun injectTimber(){
    Timber.plant(Timber.DebugTree())
}