package com.ian.app.helper.deprecated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

/**
 *
Created by Ian Damping on 03/05/2019.
Github = https://github.com/iandamping
 */
@Deprecated(
    message = "This class is still using Tuple & Tuple types and expression are no longer supported in Kotlin starting from M3"
    ,
    replaceWith = ReplaceWith(
        "LiveDataPairZipperImpl",
        "com.ian.app.helper.classes.LiveDataPairZipperImpl"
    )
    ,
    level = DeprecationLevel.WARNING
)
class GenericViewModelZipperPair<T, U>(a: LiveData<T>?, b: LiveData<U>?) : ViewModel() {
    private var customData: MediatorLiveData<Pair<T?, U?>> = MediatorLiveData()
    private var lastA: T? = null
    private var lastB: U? = null

    /*Be careful this function customize to get & pass null values*/
    private fun update() {
        val localLastA = lastA
        val localLastB = lastB
        customData.value = Pair(localLastA, localLastB)
    }

    init {
        a?.let { nonNullLiveData ->
            customData.addSource(nonNullLiveData) {
                it?.let { nonNullA ->
                    lastA = nonNullA
                    update()
                }
            }
        }
        b?.let { nonNullLiveDatas ->
            customData.addSource(nonNullLiveDatas) {
                it?.let { nonNullB ->
                    lastB = nonNullB
                    update()
                }

            }
        }
    }

    fun getGenericData(): MediatorLiveData<Pair<T?, U?>> = customData
}