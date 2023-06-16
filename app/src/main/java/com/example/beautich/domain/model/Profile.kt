package com.example.beautich.domain.model

import java.time.LocalDate

data class Profile(
    val id: String,
    val email: String,
    val fullName: String,
    val birthDate: LocalDate,
    val avatar: String?,
    val phoneNumber: String?,
    val isSubscribing: Boolean
)
