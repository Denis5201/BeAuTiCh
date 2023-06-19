package com.example.beautich.domain.repository

import com.example.beautich.domain.model.CreateChangeService
import com.example.beautich.domain.model.Service
import kotlinx.coroutines.flow.Flow

interface ServicesRepository {

    fun getAllServices(): Flow<Result<List<Service>>>

    fun getDefaultServices(): Flow<Result<List<Service>>>

    fun getCustomServices(): Flow<Result<List<Service>>>

    fun getService(): Flow<Result<Service>>

    fun createService(createService: CreateChangeService): Flow<Result<Unit>>

    fun changeService(serviceId: String, changeService: CreateChangeService): Flow<Result<Unit>>

    fun deleteService(serviceId: String): Flow<Result<Unit>>
}