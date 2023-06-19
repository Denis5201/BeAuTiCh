package com.example.beautich.data.dto

import com.example.beautich.domain.model.Service
import kotlinx.serialization.Serializable
import java.time.LocalTime

@Serializable
data class ServiceDto(
    val id: String,
    val name: String,
    val price: Double,
    val duration: String
) {
    fun toService(): Service {
        return Service(
            id = id,
            name = name,
            price = price,
            duration = LocalTime.parse(duration)
        )
    }
}
