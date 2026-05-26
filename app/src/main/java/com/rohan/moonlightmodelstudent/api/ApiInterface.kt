package com.rohan.moonlightmodelstudent.api

import com.google.gson.JsonObject
import com.rohan.moonlightmodelstudent.model.SessionsModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @POST("v1/")
    @Headers("Content-Type: application/json")
    fun login(@Body data: JsonObject) : Call<ResponseModel>

    @GET("v1/List/")
    fun getsessions(@Query("name") name:String): Call<List<SessionsModel>>
}