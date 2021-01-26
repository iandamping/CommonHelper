package com.ian.app.helper.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ian.app.helper.interfaces.LiveDataTripleZipperResult
import com.ian.app.helper.model.GenericTripleTuples
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
 *
 * How to use:
 * Use LiveDataPairZipperResult interface to communicate setter and getter
 * SAMPLE:
 * private val liveDataZipperTriple: LiveDataTripleZipperResult<String, String, String> = LiveDataTripleZipperImpl()
 * */
class LiveDataTripleZipperImpl<A, B, C> :
    LiveDataTripleZipperResult<A, B, C> {

    private var customData: MediatorLiveData<GenericTripleTuples<A, B, C>> = MediatorLiveData()
    private var lastA: A? = null
    private var lastB: B? = null
    private var lastC: C? = null

    private fun update() {
        val localLastA = lastA
        val localLastB = lastB
        val localLastC = lastC
        if (localLastA != null && localLastB != null && localLastC != null) {
            customData.value = GenericTripleTuples(
                localLastA,
                localLastB,
                localLastC
            )
        }
    }

    override fun getData(
        a: LiveData<A>?,
        b: LiveData<B>?,
        c: LiveData<C>?
    ): MediatorLiveData<GenericTripleTuples<A, B, C>> {
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

        } catch (e: Exception) {
            logE("Merge livedata failed because of ${e.message}")
        }
        return customData
    }
}