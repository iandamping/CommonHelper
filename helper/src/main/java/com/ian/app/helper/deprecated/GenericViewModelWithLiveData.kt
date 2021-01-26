package com.ian.app.helper.deprecated

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

/**
 *
Created by Ian Damping on 13/05/2019.
Github = https://github.com/iandamping
 */
@Deprecated(
    message = "This class is used when first time learn using generic in code and this class in not maintain"
    , level = DeprecationLevel.HIDDEN
)
class GenericViewModelWithLiveData<T>(a: LiveData<T>?) : ViewModel() {
    private var genericData: LiveData<T>? = a

    fun getGenericViewModelData(): LiveData<T>? = this.genericData
}