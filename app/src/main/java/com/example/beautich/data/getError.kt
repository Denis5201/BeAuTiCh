package com.example.beautich.data

import com.example.beautich.data.dto.ErrorResponse
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

fun getError(e: Exception): Throwable {
    var errorMessage = ""
    when(e) {
        is HttpException -> {
            try {
                e.response()?.errorBody()?.string()?.let {
                    errorMessage = Json.decodeFromString<ErrorResponse>(it).message ?: ""
                }
            } catch (_: Exception) {

            }
        }
        is IOException -> {
            errorMessage = "Please, check your network connection"
        }
        else -> {
            errorMessage = "Unexpected exception"
        }
    }
    return Throwable(errorMessage.ifEmpty { e.message })
}