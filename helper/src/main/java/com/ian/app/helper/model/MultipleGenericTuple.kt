package com.ian.app.helper.model


/**
 * Created by Ian Damping on 17,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

data class GenericPairTuples<A, B>(val data1: A, val data2: B)

data class GenericTripleTuples<A, B, C>(
    val data1: A,
    val data2: B,
    val data3: C
)

data class GenericQuadTuples<A, B, C, D>(
    val data1: A,
    val data2: B,
    val data3: C,
    val data4: D
)

data class GenericQuintTuples<A, B, C, D, E>(
    val data1: A,
    val data2: B,
    val data3: C,
    val data4: D,
    val data5: E
)

data class GenericSixTuples<A, B, C, D, E, F>(
    val data1: A,
    val data2: B,
    val data3: C,
    val data4: D,
    val data5: E,
    val data6: F
)