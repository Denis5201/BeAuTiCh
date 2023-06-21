package com.example.beautich.data.dto

import com.example.beautich.domain.model.Appointment
import com.example.beautich.domain.model.StatusAppointment
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class AppointmentDto(
    val id: String,
    val clientName: String,
    val clientPhone: String?,
    val services: List<ServiceShortDto>,
    val price: Double,
    val startDateTime: String,
    val endDateTime: String,
    val status: String
) {
    fun toAppointment(): Appointment {
        return Appointment(
            id = id,
            clientName = clientName,
            clientPhone = clientPhone,
            services = services.map { it.toServiceShort() },
            price = price,
            startDateTime = LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_DATE_TIME),
            endDateTime = LocalDateTime.parse(endDateTime, DateTimeFormatter.ISO_DATE_TIME),
            status = StatusAppointment.valueOf(status)
        )
    }
}
