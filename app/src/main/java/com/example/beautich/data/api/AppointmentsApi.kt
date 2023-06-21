package com.example.beautich.data.api

import com.example.beautich.data.dto.AppointmentDto
import com.example.beautich.data.dto.ChangeAppointmentDto
import com.example.beautich.data.dto.CreateAppointmentDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AppointmentsApi {

    @GET("api/appointments/timezone")
    suspend fun getAppointmentsForTime(
        @Query("startDate") startDate: String, @Query("endDate") endDate: String
    ): List<AppointmentDto>

    @GET("api/appointments/filters")
    suspend fun getAppointmentsForFilters(
        @Query("startPrice") startPrice: Double?, @Query("endPrice") endPrice: Double?,
        @Query("startDate") startDate: String?, @Query("endDate") endDate: String?,
        @Query("servicesId") servicesId: Array<String>?
    ): List<AppointmentDto>

    @GET("api/appointments/{appointmentId}")
    suspend fun getAppointment(@Path("appointmentId") appointmentId: String): AppointmentDto

    @POST("api/appointments")
    suspend fun createAppointment(@Body createAppointmentDto: CreateAppointmentDto)

    @PUT("api/appointments/{appointmentId}")
    suspend fun changeAppointment(
        @Path("appointmentId") appointmentId: String,
        @Body changeAppointmentDto: ChangeAppointmentDto
    )

    @DELETE("api/appointments/{appointmentId}")
    suspend fun deleteAppointment(@Path("appointmentId") appointmentId: String)

    @PUT("api/appointments/{appointmentId}/status")
    suspend fun changeAppointmentStatus(
        @Path("appointmentId") appointmentId: String,
        @Query("status") status: String
    )
}