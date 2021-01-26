package com.ian.app.helper.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ian.app.helper.model.GenericPairTuples


/**
 * Created by Ian Damping on 17,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface LiveDataPairZipperResult<A,B> {

    fun getData(a: LiveData<A>?, b: LiveData<B>?): MediatorLiveData<GenericPairTuples<A, B>>
}