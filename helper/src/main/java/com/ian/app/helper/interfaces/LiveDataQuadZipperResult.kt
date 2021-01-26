package com.ian.app.helper.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ian.app.helper.model.GenericQuadTuples


/**
 * Created by Ian Damping on 17,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface LiveDataQuadZipperResult<A, B, C, D> {
    fun getData(
        a: LiveData<A>?,
        b: LiveData<B>?,
        c: LiveData<C>?,
        d: LiveData<D>?
    ): MediatorLiveData<GenericQuadTuples<A, B, C, D>>
}