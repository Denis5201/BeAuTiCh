package com.example.beautich.data

import com.example.beautich.Constants
import com.example.beautich.data.api.AuthApi
import com.example.beautich.data.dto.TokenPair
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AppAuthenticator(
    private val sharedPreferences: SharedPreferences,
    private val refreshApi: AuthApi
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.responseCount > 1) {
            return null
        }

        val refreshToken = sharedPreferences.getString(SharedPreferences.REFRESH_TOKEN)
        val accessToken = sharedPreferences.getString(SharedPreferences.ACCESS_TOKEN)

        if (refreshToken.isNullOrEmpty() || accessToken.isNullOrEmpty()) {
            return null
        }

        val newTokensResult = getNewAccessTokensResult(accessToken, refreshToken)


        newTokensResult.onSuccess {
            sharedPreferences.setString(SharedPreferences.ACCESS_TOKEN, it.accessToken)
            sharedPreferences.setString(SharedPreferences.REFRESH_TOKEN, it.refreshToken)
        }

        return if (newTokensResult.isFailure) {
            response.request.newBuilder()
                .header(
                    Constants.AUTHORIZATION_HEADER,
                    "Bearer ${sharedPreferences.getString(SharedPreferences.ACCESS_TOKEN)}"
                )
                .build()
        } else {
            response.request.newBuilder()
                .header(
                    Constants.AUTHORIZATION_HEADER,
                    "Bearer ${newTokensResult.getOrThrow().accessToken}"
                )
                .build()
        }
    }

    private val Response.responseCount: Int
        get() = generateSequence(this) { it.priorResponse }.count()

    private fun getNewAccessTokensResult(accessToken: String, refreshToken: String): Result<TokenPair> {
        return runBlocking {
            try {
                val token = refreshApi.refresh(TokenPair(accessToken, refreshToken))
                Result.success(token)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
