package com.example.beautich.data.api

import com.example.beautich.data.dto.CreateWorkerUser
import com.example.beautich.data.dto.LoginCredentials
import com.example.beautich.data.dto.TokenPair
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/register")
    suspend fun register(@Body createWorkerUser: CreateWorkerUser): TokenPair

    @POST("api/auth/login")
    suspend fun login(@Body loginCredentials: LoginCredentials): TokenPair

    @POST("api/auth/refresh")
    suspend fun refresh(@Body tokenPair: TokenPair): TokenPair
}