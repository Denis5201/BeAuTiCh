package com.example.beautich.di

import com.example.beautich.data.SharedPreferences
import com.example.beautich.data.api.AuthApi
import com.example.beautich.data.api.AvatarApi
import com.example.beautich.data.api.ProfileApi
import com.example.beautich.data.repository.AuthRepositoryImpl
import com.example.beautich.data.repository.AvatarRepositoryImpl
import com.example.beautich.data.repository.LocalSettingsImpl
import com.example.beautich.data.repository.ProfileRepositoryImpl
import com.example.beautich.domain.repository.AuthRepository
import com.example.beautich.domain.repository.AvatarRepository
import com.example.beautich.domain.repository.LocalSettings
import com.example.beautich.domain.repository.ProfileRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { bindLocalSettings(get()) }
    single { bindAuthRepository(get(), get()) }
    single { bindAvatarRepository(get()) }
    single { bindProfileRepository(get()) }
}

fun bindLocalSettings(sharedPreferences: SharedPreferences): LocalSettings =
    LocalSettingsImpl(sharedPreferences)

fun bindAuthRepository(
    authApi: AuthApi,
    sharedPreferences: SharedPreferences
): AuthRepository =
    AuthRepositoryImpl(authApi, sharedPreferences)

fun bindAvatarRepository(avatarApi: AvatarApi): AvatarRepository = AvatarRepositoryImpl(avatarApi)

fun bindProfileRepository(profileApi: ProfileApi): ProfileRepository =
    ProfileRepositoryImpl(profileApi)