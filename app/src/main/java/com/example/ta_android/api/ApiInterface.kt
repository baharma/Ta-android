package com.example.ta_android.api

import com.example.ta_android.data.EventResponse
import com.example.ta_android.data.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @FormUrlEncoded
    @POST("api/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @GET("api/event")
    fun getListEvent(
        @Header("Authorization") token: String
    ): Call<EventResponse>

}