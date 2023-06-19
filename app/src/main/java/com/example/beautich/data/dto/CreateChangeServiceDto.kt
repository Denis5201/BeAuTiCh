package com.example.beautich.data.dto

import com.example.beautich.domain.model.CreateChangeService
import kotlinx.serialization.Serializable

@Serializable
data class CreateChangeServiceDto(
    val name: String,
    val price: Double,
    val duration: String
) {
    companion object {
        fun fromCreateChangeService(createChangeService: CreateChangeService): CreateChangeServiceDto {
            return CreateChangeServiceDto(
                name = createChangeService.name,
                price = createChangeService.price,
                duration = "${createChangeService.duration}:00"
            )
        }
    }
}
