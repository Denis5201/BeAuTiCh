package com.example.beautich.domain.model

import java.time.LocalDateTime

data class ChangeAppointment(
    val clientName: String,
    val clientPhone: String?,
    val startDateTime: LocalDateTime,
    val servicesId: List<String>
)
