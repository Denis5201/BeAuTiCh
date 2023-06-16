package com.example.beautich.domain.repository

interface LocalSettings {

    fun getFirstRunApp(): Boolean

    fun clearUserInfo()
}