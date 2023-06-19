package com.example.beautich

import android.content.Context

class MessageSource(context: Context) {

    private val resources = context.resources

    fun getMessage(reason: Int): String {
        return when(reason) {
            WRONG_EMAIL_FORMAT -> resources.getString(R.string.wrong_email_format)
            EMPTY_INPUT -> resources.getString(R.string.empty_input)
            PASSWORD_NOT_EQUAL_WITH_CONFIRM -> resources.getString(R.string.password_not_equal_with_confirm)
            WRONG_FORM_PASSWORD -> resources.getString(R.string.wrong_form_password)
            else -> ERROR
        }
    }

    companion object {
        const val ERROR = "Error"
        const val EMPTY_INPUT = 0
        const val WRONG_EMAIL_FORMAT = 1
        const val PASSWORD_NOT_EQUAL_WITH_CONFIRM = 2
        const val WRONG_FORM_PASSWORD = 3
    }

}