package com.example.nearfruit.networking

import com.example.nearfruit.data.response.ModelResultDetail
import com.example.nearfruit.data.response.ModelResultNearby
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("nearbysearch/json")
    fun getDataResult(
        @Query("key") key: String,
        @Query("location") location: String,
        @Query("rankby") rankby: String?,
        @Query("keyword") keyword: String,
    ): Call<ModelResultNearby>

    @GET("details/json")
    fun getDetailResult(
        @Query("key") key: String,
        @Query("placeid") placeid: String,
    ): Call<ModelResultDetail>
}