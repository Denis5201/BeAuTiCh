package com.example.beautich.domain.model

import java.time.LocalTime

data class CreateChangeService(
    val name: String,
    val price: Double,
    val duration: LocalTime
)