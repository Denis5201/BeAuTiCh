package com.example.beautich.domain.model

import java.time.LocalTime

data class Service(
    val id: String,
    val name: String,
    val price: Double,
    val duration: LocalTime
)
