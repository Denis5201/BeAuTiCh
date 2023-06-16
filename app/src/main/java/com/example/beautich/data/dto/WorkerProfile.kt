package com.example.beautich.data.dto

import com.example.beautich.domain.model.Profile
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class WorkerProfile(
    val id: String,
    val email: String,
    val fullName: String,
    val birthDate: String,
    val avatar: String?,
    val phoneNumber: String?,
    val isSubscribing: Boolean
) {
    fun toProfile(): Profile {
        return Profile(
            id = id,
            email = email,
            fullName = fullName,
            birthDate = LocalDate.parse(birthDate),
            avatar = avatar,
            phoneNumber = phoneNumber,
            isSubscribing = isSubscribing
        )
    }
}
