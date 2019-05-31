package com.ian.app.helper.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

/**
 *
Created by Ian Damping on 31/05/2019.
Github = https://github.com/iandamping
 */
class GenericNonNullViewModelZipperTriple<A, B, C>(a: LiveData<A>?, b: LiveData<B>?, c: LiveData<C>?) : ViewModel() {
    private var customData: MediatorLiveData<Triple<A, B, C>> = MediatorLiveData()
    private var lastA: A? = null
    private var lastB: B? = null
    private var lastC: C? = null

    private fun update() {
        val localLastA = lastA
        val localLastB = lastB
        val localLastC = lastC
        if (localLastA!=null && localLastB!=null && localLastC!=null){
            customData.value = Triple(localLastA, localLastB, localLastC)
        }
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
        b?.let { nonNullLiveData ->
            customData.addSource(nonNullLiveData) {
                it?.let { nonNullB ->
                    lastB = nonNullB
                    update()
                }

            }
        }

        c?.let { nonNullLiveData ->
            customData.addSource(nonNullLiveData) {
                it?.let { nonNullC ->
                    lastC = nonNullC
                    update()
                }

            }
        }
    }

    fun getGenericData(): MediatorLiveData<Triple<A, B, C>> = customData
}