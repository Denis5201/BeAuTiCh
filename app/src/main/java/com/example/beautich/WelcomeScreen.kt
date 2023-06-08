package com.example.beautich

sealed class WelcomeScreen(val route: String) {
    object StartScreen : WelcomeScreen("start")
    object SignInScreen : WelcomeScreen("login")
    object SignUpScreen : WelcomeScreen("registration")
}