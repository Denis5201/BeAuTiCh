package com.example.beautich.data.dto

import com.example.beautich.domain.model.RegistrationForm
import kotlinx.serialization.Serializable

@Serializable
data class CreateWorkerUser(
    val email: String,
    val fullName: String,
    val password: String,
    val birthDate: String,
    val phoneNumber: String?
) {
    companion object {
        fun fromRegistrationForm(registrationForm: RegistrationForm): CreateWorkerUser {
            return CreateWorkerUser(
                email = registrationForm.email,
                fullName = registrationForm.fullName,
                password = registrationForm.password,
                birthDate = registrationForm.birthDate.toString(),
                phoneNumber = registrationForm.phoneNumber
            )
        }
    }
}
