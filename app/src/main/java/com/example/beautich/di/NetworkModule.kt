package com.example.beautich.di

import com.example.beautich.Constants
import com.example.beautich.data.AppAuthenticator
import com.example.beautich.data.AuthInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single(named(Constants.AUTH_OKHTTP_CLIENT)) {
        provideAuthHttpClient()
    }
    single { AuthInterceptor(get()) }
    single<Authenticator> { AppAuthenticator(get(), get()) }
    single(named(Constants.COMMON_OKHTTP_CLIENT)) {
        provideCommonHttpClient(get(), get())
    }
    single { provideRetrofit(get(named(Constants.COMMON_OKHTTP_CLIENT))) }
}

fun provideAuthHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        val logLevel = HttpLoggingInterceptor.Level.BODY
        addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
    }.build()
}

fun provideCommonHttpClient(
    authInterceptor: AuthInterceptor,
    authenticator: Authenticator
): OkHttpClient {
    return OkHttpClient.Builder().apply {
        val logLevel = HttpLoggingInterceptor.Level.BODY
        addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
        addInterceptor(authInterceptor)
        authenticator(authenticator)
    }.build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .build()
}
