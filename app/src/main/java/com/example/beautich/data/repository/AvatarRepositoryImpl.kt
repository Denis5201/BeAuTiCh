package com.example.beautich.data.repository

import android.util.Log
import com.example.beautich.data.api.AvatarApi
import com.example.beautich.data.getError
import com.example.beautich.domain.repository.AvatarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class AvatarRepositoryImpl(
    private val api: AvatarApi
) : AvatarRepository {

    override fun getAvatar(): Flow<Result<ByteArray>> = flow {
        try {
            val inputStream = api.getAvatar().byteStream()

            val baos = ByteArrayOutputStream()
            inputStream.use {input ->
                baos.use {
                    input.copyTo(it)
                }
            }

            emit(Result.success(baos.toByteArray()))
        } catch (e: Exception) {
            Log.e("OPS getAvatar", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)


    override fun loadNewAvatar(byteArray: ByteArray): Flow<Result<Unit>> = flow {
        try {
            val body = byteArray.toRequestBody(MEDIA_PNG.toMediaType(), 0, byteArray.size)
            val part = MultipartBody.Part.createFormData(FILE, FILENAME, body)

            api.loadAvatar(part)

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS loadNewAvatar", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)


    override fun deleteAvatar(): Flow<Result<Unit>> = flow {
        try {
            api.deleteAvatar()

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS deleteAvatar", e.message.toString())
            emit(Result.failure(getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    private companion object {
        const val MEDIA_PNG = "image/png"
        const val FILE = "avatar"
        const val FILENAME = "avatar.png"
    }
}