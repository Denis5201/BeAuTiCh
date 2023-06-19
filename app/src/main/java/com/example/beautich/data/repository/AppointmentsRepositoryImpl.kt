package com.example.beautich.data.repository

import android.util.Log
import com.example.beautich.data.api.AppointmentsApi
import com.example.beautich.data.dto.CreateChangeAppointmentDto
import com.example.beautich.data.getError
import com.example.beautich.domain.model.Appointment
import com.example.beautich.domain.model.CreateChangeAppointment
import com.example.beautich.domain.model.StatusAppointment
import com.example.beautich.domain.repository.AppointmentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime

class AppointmentsRepositoryImpl(
    private val api: AppointmentsApi
) : AppointmentsRepository {

    override fun getAppointmentsForTime(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<Result<List<Appointment>>> = flow {
        try {
            val appointmentsList = api.getAppointmentsForTime(
                startDate.toString(), endDate.toString()
            ).map { it.toAppointment() }

            emit(Result.success(appointmentsList))
        } catch (e: Exception) {
            Log.e("OPS getAppointmentsForTime", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAppointmentsForFilters(
        startPrice: Double?, endPrice: Double?,
        startDate: LocalDateTime?, endDate: LocalDateTime?,
        servicesId: Array<String>?
    ): Flow<Result<List<Appointment>>> = flow {
        try {
            val appointmentsList = api.getAppointmentsForFilters(
                startPrice, endPrice,
                startDate.toString(), endDate.toString(),
                servicesId
            ).map { it.toAppointment() }

            emit(Result.success(appointmentsList))
        } catch (e: Exception) {
            Log.e("OPS getAppointmentsForFilters", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAppointment(appointmentId: String): Flow<Result<Appointment>> = flow {
        try {
            val appointment = api.getAppointment(appointmentId).toAppointment()

            emit(Result.success(appointment))
        } catch (e: Exception) {
            Log.e("OPS getAppointment", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun createAppointment(
        createAppointment: CreateChangeAppointment
    ): Flow<Result<Unit>> = flow {
        try {
            api.createAppointment(
                CreateChangeAppointmentDto.fromCreateChangeAppointment(createAppointment)
            )

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS createAppointment", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun changeAppointment(
        appointmentId: String,
        changeAppointment: CreateChangeAppointment
    ): Flow<Result<Unit>> = flow {
        try {
            api.changeAppointment(
                appointmentId,
                CreateChangeAppointmentDto.fromCreateChangeAppointment(changeAppointment)
            )

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS changeAppointment", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteAppointment(appointmentId: String): Flow<Result<Unit>> = flow {
        try {
            api.deleteAppointment(appointmentId)

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS deleteAppointment", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun changeAppointmentStatus(
        appointmentId: String,
        status: StatusAppointment
    ): Flow<Result<Unit>> = flow {
        try {
            api.changeAppointmentStatus(appointmentId, status.name)

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS changeAppointmentStatus", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)
}