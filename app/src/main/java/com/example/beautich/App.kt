package com.example.beautich

import android.app.Application
import com.example.beautich.di.apiModule
import com.example.beautich.di.appModule
import com.example.beautich.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule, networkModule, apiModule)
        }
    }
}