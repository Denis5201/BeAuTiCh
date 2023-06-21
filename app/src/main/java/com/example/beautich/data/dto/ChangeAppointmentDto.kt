package com.example.beautich.data.dto

import com.example.beautich.domain.model.ChangeAppointment
import kotlinx.serialization.Serializable

@Serializable
data class ChangeAppointmentDto(
    val clientName: String,
    val clientPhone: String?,
    val startDateTime: String,
    val servicesId: List<String>
) {
    companion object {
        fun fromChangeAppointment(
            changeAppointment: ChangeAppointment
        ): ChangeAppointmentDto {
            return ChangeAppointmentDto(
                clientName = changeAppointment.clientName,
                clientPhone = changeAppointment.clientPhone,
                startDateTime = changeAppointment.startDateTime.toString(),
                servicesId = changeAppointment.servicesId
            )
        }
    }
}