package com.example.beautich.presentation.login

sealed class SignInAction {

    object NavigateToMain : SignInAction()

    object NavigateToSignUp : SignInAction()

    data class ShowError(val message: String) : SignInAction()
}
