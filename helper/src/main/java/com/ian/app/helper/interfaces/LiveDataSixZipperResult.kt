package com.ian.app.helper.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ian.app.helper.model.GenericSixTuples


/**
 * Created by Ian Damping on 17,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface LiveDataSixZipperResult<A, B, C, D, E, F> {
    fun getData(
        a: LiveData<A>?,
        b: LiveData<B>?,
        c: LiveData<C>?,
        d: LiveData<D>?,
        e: LiveData<E>?,
        f: LiveData<F>?
    ): MediatorLiveData<GenericSixTuples<A, B, C, D, E, F>>
}