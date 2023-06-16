package com.example.beautich.data.repository

import com.example.beautich.data.SharedPreferences
import com.example.beautich.domain.repository.LocalSettings

class LocalSettingsImpl(
    private val sharedPreferences: SharedPreferences
): LocalSettings {

    override fun getFirstRunApp(): Boolean {
        return sharedPreferences.getBoolean(SharedPreferences.FIRST_RUN)
    }

    override fun clearUserInfo() {
        sharedPreferences.clearUserInfo()
    }
}