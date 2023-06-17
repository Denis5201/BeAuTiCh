package com.example.beautich.data.dto

import com.example.beautich.domain.model.ChangeProfile
import kotlinx.serialization.Serializable

@Serializable
data class ChangeUser(
    val fullName: String,
    val phoneNumber: String
) {
    companion object {
        fun fromChangeProfile(changeProfile: ChangeProfile): ChangeUser {
            return ChangeUser(
                fullName = changeProfile.fullName,
                phoneNumber = changeProfile.phoneNumber
            )
        }
    }
}
