package com.example.beautich.domain.repository

import com.example.beautich.domain.model.Credentials
import com.example.beautich.domain.model.RegistrationForm
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signIn(credentials: Credentials): Flow<Result<Unit>>

    fun signUp(registrationForm: RegistrationForm): Flow<Result<Unit>>
}