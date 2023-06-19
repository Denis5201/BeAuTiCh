package com.example.beautich.domain.repository

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface SubscriptionRepository {

    fun isSubscribing(): Flow<Result<Boolean>>

    fun changeSubscribing(isSubscribing: Boolean): Flow<Result<Unit>>

    fun getSubscribingDateTime(): Flow<Result<LocalDateTime>>
}