package com.example.beautich.data

import com.example.beautich.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = sharedPreferences.getString(SharedPreferences.ACCESS_TOKEN)

        val request = chain.request().newBuilder().apply {
            token?.let {
                addHeader(Constants.AUTHORIZATION_HEADER, "Bearer $it")
            }
        }.build()
        return chain.proceed(request)
    }
}
