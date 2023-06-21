package com.example.beautich.domain.model

import java.time.LocalDateTime

data class CreateAppointment(
    val clientName: String,
    val clientPhone: String?,
    val startDateTime: LocalDateTime,
    val idServices: List<String>
)
