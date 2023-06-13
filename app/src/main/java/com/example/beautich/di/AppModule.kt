package com.example.beautich.di

import com.example.beautich.data.SharedPreferences
import org.koin.dsl.module

val appModule = module {
    single { SharedPreferences(get()) }
}