package com.example.beautich

import java.time.format.DateTimeFormatter

object Constants {
    const val BASE_URL = "http://94.250.248.129:10000/"
    const val AUTHORIZATION_HEADER = "Authorization"
    const val AUTH_OKHTTP_CLIENT = "auth"
    const val COMMON_OKHTTP_CLIENT = "common"
    const val IMAGE_NAME = "temp_photo"
    const val JPEG = ".jpg"
    const val APPOINTMENT_ID = "appointment_id"
    const val FROM_DEVELOP = "from_develop"
    val dateTimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
}