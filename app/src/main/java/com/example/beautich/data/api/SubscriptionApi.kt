package com.example.beautich.data.api

import com.example.beautich.data.dto.Subscribe
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface SubscriptionApi {

    @GET("api/subscribe")
    suspend fun isSubscribing(): Boolean

    @PUT("api/subscribe")
    suspend fun changeSubscribing(@Query("isSubscribing") isSubscribing: Boolean)

    @GET("api/subscribe/details")
    suspend fun getSubscribingDateTime(): Subscribe
}