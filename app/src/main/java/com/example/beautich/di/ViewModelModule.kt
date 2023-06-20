package com.example.beautich.di

import com.example.beautich.presentation.appointment.details.AppointmentDetailsViewModel
import com.example.beautich.presentation.appointment.develop.AppointmentDevelopViewModel
import com.example.beautich.presentation.login.SignInViewModel
import com.example.beautich.presentation.main.MainViewModel
import com.example.beautich.presentation.profile.ProfileViewModel
import com.example.beautich.presentation.registration.SignUpViewModel
import com.example.beautich.presentation.search.SearchViewModel
import com.example.beautich.presentation.service_selection.ServiceSelectionViewModel
import com.example.beautich.presentation.settings.MyServicesViewModel
import com.example.beautich.presentation.settings.SubscriptionViewModel
import com.example.beautich.presentation.start.StartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StartViewModel(get()) }
    viewModel { SignInViewModel(get(), get(), get(), get()) }
    viewModel { SignUpViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { SubscriptionViewModel(get()) }
    viewModel { MyServicesViewModel(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { AppointmentDevelopViewModel(get(), get()) }
    viewModel { AppointmentDetailsViewModel(get(), get()) }
    viewModel { ServiceSelectionViewModel(get(), get()) }
}