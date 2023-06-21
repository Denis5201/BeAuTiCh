package com.example.beautich.data.dto

import com.example.beautich.domain.model.CreateAppointment
import kotlinx.serialization.Serializable

@Serializable
data class CreateAppointmentDto(
    val clientName: String,
    val clientPhone: String?,
    val startDateTime: String,
    val idServices: List<String>
) {
    companion object {
        fun fromCreateAppointment(
            createAppointment: CreateAppointment
        ): CreateAppointmentDto {
            return CreateAppointmentDto(
                clientName = createAppointment.clientName,
                clientPhone = createAppointment.clientPhone,
                startDateTime = createAppointment.startDateTime.toString(),
                idServices = createAppointment.idServices
            )
        }
    }
}
