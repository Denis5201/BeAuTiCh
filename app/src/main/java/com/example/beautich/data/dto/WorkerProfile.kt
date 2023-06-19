package com.example.beautich.data.dto

import com.example.beautich.domain.model.Profile
import kotlinx.serialization.Serializable

@Serializable
data class WorkerProfile(
    val id: String,
    val email: String,
    val fullName: String,
    val avatar: Boolean,
    val phoneNumber: String?,
    val isSubscribing: Boolean
) {
    fun toProfile(): Profile {
        return Profile(
            id = id,
            email = email,
            fullName = fullName,
            avatar = avatar,
            phoneNumber = phoneNumber,
            isSubscribing = isSubscribing
        )
    }
}
