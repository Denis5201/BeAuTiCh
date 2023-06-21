package com.example.beautich.data.api

import com.example.beautich.data.dto.ChangePassword
import com.example.beautich.data.dto.ChangeUser
import com.example.beautich.data.dto.WorkerProfile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

interface ProfileApi {

    @GET("api/profile")
    suspend fun getProfile(): WorkerProfile

    @PATCH("api/profile")
    suspend fun changeProfile(@Body changeUser: ChangeUser)

    @PUT("api/profile/password")
    suspend fun changePassword(@Body changePassword: ChangePassword)

    @POST("api/auth/logout")
    suspend fun logout()
}