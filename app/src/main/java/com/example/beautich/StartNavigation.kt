package com.example.beautich

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.beautich.presentation.login.SignInScreen
import com.example.beautich.presentation.registration.SignUpScreen
import com.example.beautich.presentation.start.StartScreen

@Composable
fun StartNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WelcomeScreen.StartScreen.route) {
        composable(WelcomeScreen.StartScreen.route) {
           StartScreen(navController)
        }
        composable(WelcomeScreen.SignInScreen.route) {
            SignInScreen(navController)
        }
        composable(WelcomeScreen.SignUpScreen.route) {
            SignUpScreen(navController)
        }
    }
}