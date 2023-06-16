package com.example.beautich.domain.model

import java.time.LocalDate

data class ChangeProfile(
    val fullName: String,
    val birthDate: LocalDate,
    val avatar: String,
    val phoneNumber: String
)
