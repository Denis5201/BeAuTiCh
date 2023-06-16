package com.example.beautich.domain

import android.util.Patterns

class Validator {

    fun isStringsEmpty(vararg field: String): Boolean {
        for (str in field) {
            if (str.isEmpty()) {
                return true
            }
        }
        return false
    }

    fun isEmailFormat(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordForm(password: String): Boolean {
        if (password.length > 5 && password.contains(Regex("[a-z]"))
            && password.contains(Regex("[A-Z]")) && password.contains(Regex("[0-9]"))
        ) {
            return true
        }
        return false
    }
}