package com.ian.app.helper.di

import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Ian Damping on 30,October,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */

inline fun <reified T> helperNetworkModule(baseUrl: String) = module {
    single { createOkHttpClient() }
    single { createClient<T>(get(), baseUrl) }
}


fun createOkHttpClient(): OkHttpClient {
    val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .dispatcher(Dispatcher().apply {
            maxRequests = 20
            maxRequestsPerHost = 20
        })
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            chain.run { proceed(this.request().newBuilder().build()) }
        }
    return okHttpBuilder.build()
}

inline fun <reified T> createClient(okHttpClient: OkHttpClient, baseUrl: String): T {
    val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(baseUrl)
        .build()
    return retrofit.create(T::class.java)
}
