package com.example.beautich.domain.model

data class Profile(
    val id: String,
    val email: String,
    val fullName: String,
    val avatar: Boolean,
    val phoneNumber: String?,
    val isSubscribing: Boolean
)
