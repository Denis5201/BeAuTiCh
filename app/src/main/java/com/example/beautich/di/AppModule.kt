package com.example.beautich.di

import com.example.beautich.MessageSource
import com.example.beautich.data.SharedPreferences
import com.example.beautich.domain.Validator
import org.koin.dsl.module

val appModule = module {
    single { SharedPreferences(get()) }
    single { MessageSource(get()) }
    single { Validator() }
}