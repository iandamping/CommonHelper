package com.ian.app.helper.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 *
Created by Ian Damping on 26/06/2019.
Github = https://github.com/iandamping
 */
/*For Fragment*/
inline fun <reified T : ViewModel> Fragment.fragmentGetViewModelHelper(crossinline factory: () -> T): T {
    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
    return ViewModelProvider(this, vmFactory)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.customViewModelFactoriesHelper(
    crossinline factory: () -> T,
    body: T.() -> Unit
): T {
    val vm = fragmentGetViewModelHelper(factory)
    vm.body()
    return vm
}

/*For Activity*/
inline fun <reified T : ViewModel> FragmentActivity.activityGetViewModelHelper(crossinline factory: () -> T): T {
    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
    return ViewModelProvider(this, vmFactory)[T::class.java]
}

inline fun <reified T : ViewModel> FragmentActivity.customViewModelFactoriesHelper(
    crossinline factory: () -> T,
    body: T.() -> Unit
): T {
    val vm = activityGetViewModelHelper(factory)
    vm.body()
    return vm
}