package com.ian.app.helper.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ian.app.helper.interfaces.LiveDataQuadZipperResult
import com.ian.app.helper.model.GenericQuadTuples
import com.ian.app.helper.util.logE


/**
 * Created by Ian Damping on 17,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 *
 * This class is used to merge pair livedata into one
 * @Param A is the first wanted livedata type
 * @param B is the second wanted livedata type
 * @param C is the third wanted livedata type
 * @param D is the fourth wanted livedata type
 *
 * How to use:
 * Use LiveDataPairZipperResult interface to communicate setter and getter
 * SAMPLE:
 * private val liveDataZipperQuad: LiveDataQuadZipperResult<String, String,String, String> = LiveDataQuadZipperImpl()
 * */

class LiveDataQuadZipperImpl<A, B, C, D> :
    LiveDataQuadZipperResult<A, B, C, D> {

    private var customData: MediatorLiveData<GenericQuadTuples<A, B, C, D>> = MediatorLiveData()
    private var lastA: A? = null
    private var lastB: B? = null
    private var lastC: C? = null
    private var lastD: D? = null

    private fun update() {
        val localLastA = lastA
        val localLastB = lastB
        val localLastC = lastC
        val localLastD = lastD
        if (localLastA != null && localLastB != null && localLastC != null && localLastD != null) {
            customData.value = GenericQuadTuples(
                localLastA,
                localLastB,
                localLastC,
                localLastD
            )
        }
    }

    override fun getData(
        a: LiveData<A>?,
        b: LiveData<B>?,
        c: LiveData<C>?,
        d: LiveData<D>?
    ): MediatorLiveData<GenericQuadTuples<A, B, C, D>> {
        try {
            requireNotNull(a) {
                "First value passed is null"
            }
            requireNotNull(b) {
                "Second value passed is null"
            }
            requireNotNull(c) {
                "Third value passed is null"
            }
            requireNotNull(d) {
                "Third value passed is null"
            }
            customData.addSource(a) { result ->
                lastA = result
                update()
            }

            customData.addSource(b) { result ->
                lastB = result
                update()
            }

            customData.addSource(c) { result ->
                lastC = result
                update()
            }

            customData.addSource(d) { result ->
                lastD = result
                update()
            }

        } catch (e: Exception) {
            logE("Merge livedata failed because of ${e.message}")
        }
        return customData
    }
}