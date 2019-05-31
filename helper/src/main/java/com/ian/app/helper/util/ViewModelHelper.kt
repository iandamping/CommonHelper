package com.ian.app.helper.util


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 *
Created by Ian Damping on 25/05/2019.
Github = https://github.com/iandamping
 */

inline fun <reified T : ViewModel> FragmentActivity.viewModelHelperForActivity(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}

inline fun <reified T : ViewModel> FragmentActivity.withViewModel(body: T.() -> Unit): T {
    val vm = viewModelHelperForActivity<T>()
    vm.body()
    return vm
}


inline fun <reified T : ViewModel> FragmentActivity.activityGetViewModelHelper(crossinline factory: () -> T): T {
    val vmFactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
    return ViewModelProviders.of(this, vmFactory)[T::class.java]
}

/*Factory helper for Activity*/
inline fun <reified T : ViewModel> FragmentActivity.customViewModelFactoriesHelper(
    crossinline factory: () -> T,
    body: T.() -> Unit
): T {
    val vm = activityGetViewModelHelper(factory)
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> Fragment.fragmentGetViewModelHelper(crossinline factory: () -> T): T {
    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
    return ViewModelProviders.of(this, vmFactory)[T::class.java]
}
/*Factory helper for Fragment*/
inline fun <reified T : ViewModel> Fragment.customViewModelFactoriesHelper(
    crossinline factory: () -> T,
    body: T.() -> Unit
): T {
    val vm = fragmentGetViewModelHelper(factory)
    vm.body()
    return vm
}


// how to use customViewModelFactoriesHelper
/*getLifeCycleOwner().customViewModelFactoriesHelper({ IncomeViewByDate(DatabasesAccess, firstDay, lastDay) }) {
                    with(this) {
                        getDataById()?.observe(getLifeCycleOwner().viewLifecycleOwner, Observer {
                            when {
                                it != null -> {
                                    view()?.showDataIncome(it)
                                    incomeValue = it
                                    balanceViewModel.setBalanceData(incomeValue?.let { incomeNonNull ->
                                        expenseValue?.plus(
                                                incomeNonNull
                                        )
                                    })
                                }
                                else -> incomeValue = 0
                            }
                        })
                    }
                }*/




