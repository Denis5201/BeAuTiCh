package com.example.beautich.domain.model

import java.time.LocalDate

data class RegistrationForm(
    val email: String,
    val fullName: String,
    val password: String,
    val birthDate: LocalDate,
    val phoneNumber: String?
)
