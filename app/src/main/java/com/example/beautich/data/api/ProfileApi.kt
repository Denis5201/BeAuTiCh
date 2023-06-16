package com.example.beautich.data.api

import com.example.beautich.data.dto.ChangeUser
import com.example.beautich.data.dto.WorkerProfile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ProfileApi {

    @GET("api/profile")
    suspend fun getProfile(): WorkerProfile

    @PATCH("api/profile")
    suspend fun changeProfile(@Body changeUser: ChangeUser)

    @PUT("api/profile/password")
    suspend fun changePassword()//TODO: Возможно что-то изменится, поэтому пока без реализации

    @POST("api/auth/logout")
    suspend fun logout()

    @GET("api/profile/subscribe")
    suspend fun isSubscribing(): Boolean

    @PUT("api/profile/subscribe")
    suspend fun changeSubscribing(@Query("isSubscribing") isSubscribing: Boolean)
}