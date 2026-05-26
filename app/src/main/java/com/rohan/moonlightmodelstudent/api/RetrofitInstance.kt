package com.rohan.moonlightmodelstudent.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance(val context: Context) {
    init{
        System.loadLibrary("keys")
    }
    private external fun getApiurl():String

     val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    fun gfgHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .addInterceptor(ResponseInterceptor(context))
        return builder.build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(getApiurl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(gfgHttpClient())
            .build()
    }

    val apiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}