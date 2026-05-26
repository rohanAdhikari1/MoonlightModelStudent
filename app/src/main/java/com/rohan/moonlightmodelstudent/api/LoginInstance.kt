package com.rohan.moonlightmodelstudent.api

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginInstance(val context: Context) {
        init{
            System.loadLibrary("keys")
        }
        private external fun getApiurl():String

        fun gfgHttpClient(): OkHttpClient {
            val builder = OkHttpClient().newBuilder()
            .addInterceptor(NetworkConnectionInterceptor(context))
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