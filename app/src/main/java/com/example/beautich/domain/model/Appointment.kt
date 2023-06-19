package com.example.beautich.domain.model

import java.time.LocalDateTime

data class Appointment(
    val id: String,
    val clientName: String,
    val services: List<ServiceShort>,
    val price: Double,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val status: StatusAppointment
)
