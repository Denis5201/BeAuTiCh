package com.example.beautich.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateWorkerUser(
    val email: String,
    val fullName: String,
    val password: String,
    val birthDate: String,
    val phoneNumber: String,
)
