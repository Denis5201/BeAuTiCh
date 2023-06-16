package com.example.beautich.presentation.start

sealed class StartAction {
    object NavigateToSignUp : StartAction()
    object NavigateToSignIn : StartAction()
}
