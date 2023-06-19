package com.example.beautich.domain.model

import java.time.LocalDateTime

data class CreateChangeAppointment(
    val clientName: String,
    val clientPhone: String,
    val startDateTime: LocalDateTime,
    val servicesId: List<String>
)
