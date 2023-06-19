package com.example.beautich.data.api

import com.example.beautich.data.dto.CreateChangeServiceDto
import com.example.beautich.data.dto.ServiceDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServicesApi {

    @GET("api/services/all")
    suspend fun getAllServices(): List<ServiceDto>

    @GET("api/services/default")
    suspend fun getDefaultServices(): List<ServiceDto>

    @GET("api/services/custom")
    suspend fun getCustomServices(): List<ServiceDto>

    @GET("api/services/{serviceId}")
    suspend fun getService(): ServiceDto

    @POST("api/services")
    suspend fun createService(@Body createServiceDto: CreateChangeServiceDto)

    @PUT("api/services/{serviceId}")
    suspend fun changeService(
        @Path("serviceId") serviceId: String,
        @Body changeServiceDto: CreateChangeServiceDto
    )

    @DELETE("api/services/{serviceId}")
    suspend fun deleteService(@Path("serviceId") serviceId: String)
}