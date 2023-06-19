package com.example.beautich.domain.model

data class RegistrationForm(
    val email: String,
    val fullName: String,
    val password: String,
    val phoneNumber: String?
)
