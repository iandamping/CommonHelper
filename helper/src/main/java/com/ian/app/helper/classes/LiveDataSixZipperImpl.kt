package com.ian.app.helper.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ian.app.helper.interfaces.LiveDataSixZipperResult
import com.ian.app.helper.model.GenericSixTuples
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
 * @param E is the fifth wanted livedata type
 * @param F is the Sixth wanted livedata type
 *
 * How to use:
 * Use LiveDataPairZipperResult interface to communicate setter and getter
 * SAMPLE:
 * private val liveDataZipperSix: LiveDataSixZipperResult<String, String,String, String, String, String> = LiveDataSixZipperImpl()
 * */
class LiveDataSixZipperImpl<A, B, C, D, E, F> :
    LiveDataSixZipperResult<A, B, C, D, E, F> {
    private var customData: MediatorLiveData<GenericSixTuples<A, B, C, D, E, F>> =
        MediatorLiveData()
    private var lastA: A? = null
    private var lastB: B? = null
    private var lastC: C? = null
    private var lastD: D? = null
    private var lastE: E? = null
    private var lastF: F? = null

    private fun update() {
        val localLastA = lastA
        val localLastB = lastB
        val localLastC = lastC
        val localLastD = lastD
        val localLastE = lastE
        val localLastF = lastF
        if (localLastA != null && localLastB != null && localLastC != null && localLastD != null
            && localLastE != null && localLastF != null
        ) {
            customData.value =
                GenericSixTuples(
                    localLastA,
                    localLastB,
                    localLastC,
                    localLastD,
                    localLastE,
                    localLastF
                )
        }
    }

    override fun getData(
        a: LiveData<A>?,
        b: LiveData<B>?,
        c: LiveData<C>?,
        d: LiveData<D>?,
        e: LiveData<E>?,
        f: LiveData<F>?
    ): MediatorLiveData<GenericSixTuples<A, B, C, D, E, F>> {
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
            requireNotNull(e) {
                "Third value passed is null"
            }
            requireNotNull(f) {
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
            customData.addSource(e) { result ->
                lastE = result
                update()
            }
            customData.addSource(f) { result ->
                lastF = result
                update()
            }
        } catch (e: Exception) {
            logE("Merge livedata failed because of ${e.message}")
        }
        return customData
    }
}