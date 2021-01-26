package com.ian.app.helper.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ian.app.helper.interfaces.LiveDataPairZipperResult
import com.ian.app.helper.model.GenericPairTuples
import com.ian.app.helper.util.logE

/**
 * Created by Ian Damping on 17,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 *
 * This class is used to merge pair livedata into one
 * @Param A is the first wanted livedata type
 * @param B is the second wanted livedata type
 *
 * How to use:
 * Use LiveDataPairZipperResult interface to communicate setter and getter
 * SAMPLE:
 * private val liveDataZipperPair: LiveDataPairZipperResult<String, String> = LiveDataPairZipperImpl()
 * */
class LiveDataPairZipperImpl<A, B> :
    LiveDataPairZipperResult<A, B> {
    private val customData: MediatorLiveData<GenericPairTuples<A, B>> = MediatorLiveData()
    private var lastA: A? = null
    private var lastB: B? = null

    private fun update() {
        val localLastA = lastA
        val localLastB = lastB
        if (localLastA != null && localLastB != null) {
            customData.value = GenericPairTuples(
                localLastA,
                localLastB
            )
        }
    }

    override fun getData(
        a: LiveData<A>?,
        b: LiveData<B>?
    ): MediatorLiveData<GenericPairTuples<A, B>> {

        try {
            requireNotNull(a) {
                "First value passed is null"
            }
            requireNotNull(b) {
                "Second value passed is null"
            }
            customData.addSource(a) { result ->
                lastA = result
                update()
            }

            customData.addSource(b) { result ->
                lastB = result
                update()
            }

        } catch (e: Exception) {
            logE("Merge livedata failed because of ${e.message}")
        }
        return customData

    }
}