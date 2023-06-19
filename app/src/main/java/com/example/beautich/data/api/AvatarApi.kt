package com.example.beautich.data.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Streaming

interface AvatarApi {

    @Streaming
    @GET("api/profile/avatar")
    suspend fun getAvatar(): ResponseBody

    @Multipart
    @POST("api/profile/avatar")
    suspend fun loadAvatar(@Part file: MultipartBody.Part)

    @DELETE("api/profile/avatar")
    suspend fun deleteAvatar()
}