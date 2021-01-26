package com.ian.app.helper.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ian.app.helper.model.GenericTripleTuples


/**
 * Created by Ian Damping on 17,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface LiveDataTripleZipperResult<A, B, C> {

    fun getData(
        a: LiveData<A>?,
        b: LiveData<B>?,
        c: LiveData<C>?
    ): MediatorLiveData<GenericTripleTuples<A, B, C>>
}