package com.example.beautich.di

import com.example.beautich.Constants
import com.example.beautich.data.api.AppointmentsApi
import com.example.beautich.data.api.AuthApi
import com.example.beautich.data.api.AvatarApi
import com.example.beautich.data.api.ProfileApi
import com.example.beautich.data.api.ServicesApi
import com.example.beautich.data.api.SubscriptionApi
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
    single { provideSubscriptionApi(get()) }
    single { provideServicesApi(get()) }
    single { provideAppointmentsApi(get()) }
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

fun provideSubscriptionApi(retrofit: Retrofit): SubscriptionApi =
    retrofit.create(SubscriptionApi::class.java)

fun provideServicesApi(retrofit: Retrofit): ServicesApi = retrofit.create(ServicesApi::class.java)

fun provideAppointmentsApi(retrofit: Retrofit): AppointmentsApi =
    retrofit.create(AppointmentsApi::class.java)