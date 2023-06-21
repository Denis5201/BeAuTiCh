package com.example.beautich.data.dto

import com.example.beautich.domain.model.CreateChangeAppointment
import kotlinx.serialization.Serializable

@Serializable
data class CreateChangeAppointmentDto(
    val clientName: String,
    val clientPhone: String?,
    val startDateTime: String,
    val idServices: List<String>
) {
    companion object {
        fun fromCreateChangeAppointment(
            createChangeAppointment: CreateChangeAppointment
        ): CreateChangeAppointmentDto {
            return CreateChangeAppointmentDto(
                clientName = createChangeAppointment.clientName,
                clientPhone = createChangeAppointment.clientPhone,
                startDateTime = createChangeAppointment.startDateTime.toString(),
                idServices = createChangeAppointment.servicesId
            )
        }
    }
}
