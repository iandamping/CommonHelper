package com.ian.app.helper.deprecated

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
@Deprecated(
    message = "This class is used when first time learn using generic in code and this class in not maintain"
    , level = DeprecationLevel.HIDDEN
)
class GenericViewModel<T> : ViewModel() {
    private var customData: MutableLiveData<T> = MutableLiveData()

    fun setGenericData(data: T) {
        this.customData.value = data
    }

    fun getGenericData(): MutableLiveData<T> = customData
}