package com.example.beautich.presentation.registration

sealed class SignUpAction {

    object NavigateToMain : SignUpAction()

    object NavigateToSignIn : SignUpAction()

    data class ShowError(val message: String) : SignUpAction()
}
