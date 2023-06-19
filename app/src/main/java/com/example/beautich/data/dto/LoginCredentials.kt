package com.example.beautich.data.dto

import com.example.beautich.domain.model.Credentials
import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentials(
    val email: String,
    val password: String
) {
    companion object {
        fun fromCredentials(credentials: Credentials): LoginCredentials {
            return LoginCredentials(
                email = credentials.email,
                password = credentials.password
            )
        }
    }
}
