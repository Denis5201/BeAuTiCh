package com.example.beautich.data.repository

import android.util.Log
import com.example.beautich.data.SharedPreferences
import com.example.beautich.data.api.AuthApi
import com.example.beautich.data.dto.CreateWorkerUser
import com.example.beautich.data.dto.LoginCredentials
import com.example.beautich.data.getError
import com.example.beautich.domain.model.Credentials
import com.example.beautich.domain.model.RegistrationForm
import com.example.beautich.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override fun signIn(credentials: Credentials): Flow<Result<Unit>> = flow {
        try {
            val tokenPair = api.login(LoginCredentials.fromCredentials(credentials))

            sharedPreferences.setString(SharedPreferences.ACCESS_TOKEN, tokenPair.accessToken)
            sharedPreferences.setString(SharedPreferences.REFRESH_TOKEN, tokenPair.refreshToken)
            sharedPreferences.setBoolean(SharedPreferences.FIRST_RUN, false)

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS signIn", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun signUp(registrationForm: RegistrationForm): Flow<Result<Unit>> = flow {
        try {
            val tokenPair =  api.register(CreateWorkerUser.fromRegistrationForm(registrationForm))

            sharedPreferences.setString(SharedPreferences.ACCESS_TOKEN, tokenPair.accessToken)
            sharedPreferences.setString(SharedPreferences.REFRESH_TOKEN, tokenPair.refreshToken)
            sharedPreferences.setBoolean(SharedPreferences.FIRST_RUN, false)

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS signUp", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

}