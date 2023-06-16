package com.example.beautich.data.repository

import android.util.Log
import com.example.beautich.data.api.ProfileApi
import com.example.beautich.data.dto.ChangeUser
import com.example.beautich.data.getError
import com.example.beautich.domain.model.ChangeProfile
import com.example.beautich.domain.model.Profile
import com.example.beautich.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileRepositoryImpl(
    private val api: ProfileApi
) : ProfileRepository {

    override fun getProfile(): Flow<Result<Profile>> = flow {
        try {
            val profile =  api.getProfile().toProfile()

            emit(Result.success(profile))
        } catch (e: Exception) {
            Log.e("OPS getProfile", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun changeProfile(changeProfile: ChangeProfile): Flow<Result<Unit>> = flow {
        try {
            api.changeProfile(ChangeUser.fromChangeProfile(changeProfile))

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS changeProfile", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun changePassword(): Flow<Result<Unit>> = flow {
        try {
            api.changePassword()

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS changePassword", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun logout(): Flow<Result<Unit>> = flow {
        try {
            api.logout()

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS logout", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun isSubscribing(): Flow<Result<Boolean>> = flow {
        try {
            val isSubscribing = api.isSubscribing()

            emit(Result.success(isSubscribing))
        } catch (e: Exception) {
            Log.e("OPS isSubscribing", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun changeSubscribing(isSubscribing: Boolean): Flow<Result<Unit>> = flow {
        try {
            api.changeSubscribing(isSubscribing)

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS changeSubscribing", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)
}