package com.example.beautich.domain.repository

import com.example.beautich.domain.model.Appointment
import com.example.beautich.domain.model.CreateChangeAppointment
import com.example.beautich.domain.model.StatusAppointment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface AppointmentsRepository {

    fun getAppointmentsForTime(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<Result<List<Appointment>>>

    fun getAppointmentsForFilters(
        startPrice: Double?, endPrice: Double?,
        startDate: LocalDateTime?, endDate: LocalDateTime?,
        servicesId: Array<String>?
    ): Flow<Result<List<Appointment>>>

    fun getAppointment(appointmentId: String): Flow<Result<Appointment>>

    fun createAppointment(createAppointment: CreateChangeAppointment): Flow<Result<Unit>>

    fun changeAppointment(
        appointmentId: String,
        changeAppointment: CreateChangeAppointment
    ): Flow<Result<Unit>>

    fun deleteAppointment(appointmentId: String): Flow<Result<Unit>>

    fun changeAppointmentStatus(
        appointmentId: String,
        status: StatusAppointment
    ): Flow<Result<Unit>>
}