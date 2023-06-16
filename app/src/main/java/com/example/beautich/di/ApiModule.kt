package com.example.beautich.di

import com.example.beautich.Constants
import com.example.beautich.data.api.AuthApi
import com.example.beautich.data.api.AvatarApi
import com.example.beautich.data.api.ProfileApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { provideAuthApi(get(named(Constants.AUTH_OKHTTP_CLIENT))) }
    single { provideAvatarApi(get()) }
    single { provideProfileApi(get()) }
}

fun provideAuthApi(okHttpClient: OkHttpClient): AuthApi {
    val authRetrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .build()
    return authRetrofit.create(AuthApi::class.java)
}

fun provideAvatarApi(retrofit: Retrofit): AvatarApi = retrofit.create(AvatarApi::class.java)

fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)