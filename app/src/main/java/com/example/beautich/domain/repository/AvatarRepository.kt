package com.example.beautich.domain.repository

import kotlinx.coroutines.flow.Flow

interface AvatarRepository {

    fun getAvatar(): Flow<Result<ByteArray>>

    fun loadNewAvatar(byteArray: ByteArray): Flow<Result<Unit>>

    fun deleteAvatar(): Flow<Result<Unit>>
}