package com.example.beautich.data.dto

import com.example.beautich.domain.model.ServiceShort
import kotlinx.serialization.Serializable

@Serializable
data class ServiceShortDto(
    val id: String,
    val name: String
) {
    fun toServiceShort(): ServiceShort {
        return ServiceShort(
            id = id,
            name = name
        )
    }
}
