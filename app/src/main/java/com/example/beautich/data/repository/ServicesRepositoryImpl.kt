package com.example.beautich.data.repository

import android.util.Log
import com.example.beautich.data.api.ServicesApi
import com.example.beautich.data.dto.CreateChangeServiceDto
import com.example.beautich.data.getError
import com.example.beautich.domain.model.CreateChangeService
import com.example.beautich.domain.model.Service
import com.example.beautich.domain.repository.ServicesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ServicesRepositoryImpl(
    private val api: ServicesApi
) : ServicesRepository {

    override fun getAllServices(): Flow<Result<List<Service>>> = flow {
        try {
            val servicesList = api.getAllServices().map { it.toService() }

            emit(Result.success(servicesList))
        } catch (e: Exception) {
            Log.e("OPS getAllServices", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun getDefaultServices(): Flow<Result<List<Service>>> = flow {
        try {
            val servicesList = api.getDefaultServices().map { it.toService() }

            emit(Result.success(servicesList))
        } catch (e: Exception) {
            Log.e("OPS getDefaultServices", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun getCustomServices(): Flow<Result<List<Service>>> = flow {
        try {
            val servicesList = api.getCustomServices().map { it.toService() }

            emit(Result.success(servicesList))
        } catch (e: Exception) {
            Log.e("OPS getCustomServices", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun getService(): Flow<Result<Service>> = flow {
        try {
            val service = api.getService().toService()

            emit(Result.success(service))
        } catch (e: Exception) {
            Log.e("OPS getService", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun createService(createService: CreateChangeService): Flow<Result<Unit>> = flow {
        try {
            api.createService(CreateChangeServiceDto.fromCreateChangeService(createService))

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS createService", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun changeService(
        serviceId: String,
        changeService: CreateChangeService
    ): Flow<Result<Unit>> = flow {
        try {
            api.changeService(
                serviceId, CreateChangeServiceDto.fromCreateChangeService(changeService)
            )

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS changeService", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteService(serviceId: String): Flow<Result<Unit>> = flow {
        try {
            api.deleteService(serviceId)

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS deleteService", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)
}