package com.example.beautich.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChangePassword(
    val oldPassword: String,
    val newPassword: String
)
