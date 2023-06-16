package com.example.beautich.di

import com.example.beautich.presentation.login.SignInViewModel
import com.example.beautich.presentation.profile.ProfileViewModel
import com.example.beautich.presentation.registration.SignUpViewModel
import com.example.beautich.presentation.start.StartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StartViewModel(get()) }
    viewModel { SignInViewModel(get(), get(), get(), get()) }
    viewModel { SignUpViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
}