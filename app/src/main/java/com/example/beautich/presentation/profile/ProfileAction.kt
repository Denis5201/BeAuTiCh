package com.example.beautich.presentation.profile

sealed class ProfileAction {

    object Logout : ProfileAction()

    data class ShowError(val message: String) : ProfileAction()
}
