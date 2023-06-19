package com.example.beautich.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.beautich.presentation.login.SignInScreen
import com.example.beautich.presentation.login.SignInViewModel
import com.example.beautich.presentation.registration.SignUpScreen
import com.example.beautich.presentation.registration.SignUpViewModel
import com.example.beautich.presentation.settings.MyServicesScreen
import com.example.beautich.presentation.settings.MyServicesViewModel
import com.example.beautich.presentation.settings.SubscriptionScreen
import com.example.beautich.presentation.settings.SubscriptionViewModel
import com.example.beautich.presentation.start.StartScreen
import com.example.beautich.presentation.start.StartViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.StartScreen.route) {
        composable(Screen.StartScreen.route) {
            val viewModel = koinViewModel<StartViewModel>()
            StartScreen(navController, viewModel)
        }
        composable(Screen.SignInScreen.route) {
            val viewModel = koinViewModel<SignInViewModel>()
            SignInScreen(navController, viewModel)
        }
        composable(Screen.SignUpScreen.route) {
            val viewModel = koinViewModel<SignUpViewModel>()
            SignUpScreen(navController, viewModel)
        }
        composable(Screen.BottomNavigationScreen.route) {
            BottomNavigation(navController)
        }
        composable(Screen.SubscriptionScreen.route) {
            val viewModel = koinViewModel<SubscriptionViewModel>()
            SubscriptionScreen(navController, viewModel)
        }
        composable(Screen.MyServicesScreen.route) {
            val viewModel = koinViewModel<MyServicesViewModel>()
            MyServicesScreen(navController, viewModel)
        }
    }
}