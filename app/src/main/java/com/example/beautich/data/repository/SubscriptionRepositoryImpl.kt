package com.example.beautich.data.repository

import android.util.Log
import com.example.beautich.data.api.SubscriptionApi
import com.example.beautich.data.getError
import com.example.beautich.domain.repository.SubscriptionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class SubscriptionRepositoryImpl(
    private val api: SubscriptionApi
) : SubscriptionRepository {

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

    override fun getSubscribingDateTime(): Flow<Result<LocalDateTime>> = flow {
        try {
            val dateTimeString = api.getSubscribingDateTime().createDate

            val zoneId = ZoneId.systemDefault()
            val dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME)
                .atOffset(ZoneOffset.UTC).atZoneSameInstant(zoneId).toLocalDateTime()

            emit(Result.success(dateTime))
        } catch (e: Exception) {
            Log.e("OPS getSubscribingDateTime", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)
}