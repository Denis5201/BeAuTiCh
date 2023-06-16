package com.example.beautich.domain.repository

import com.example.beautich.domain.model.ChangeProfile
import com.example.beautich.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<Result<Profile>>

    fun changeProfile(changeProfile: ChangeProfile): Flow<Result<Unit>>

    fun changePassword(): Flow<Result<Unit>>

    fun logout(): Flow<Result<Unit>>

    fun isSubscribing(): Flow<Result<Boolean>>

    fun changeSubscribing(isSubscribing: Boolean): Flow<Result<Unit>>
}