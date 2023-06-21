package com.example.beautich.presentation.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.beautich.Constants
import com.example.beautich.presentation.appointment.details.AppointmentDetailsScreen
import com.example.beautich.presentation.appointment.details.AppointmentDetailsViewModel
import com.example.beautich.presentation.appointment.develop.AppointmentDevelopScreen
import com.example.beautich.presentation.appointment.develop.AppointmentDevelopViewModel
import com.example.beautich.presentation.login.SignInScreen
import com.example.beautich.presentation.login.SignInViewModel
import com.example.beautich.presentation.registration.SignUpScreen
import com.example.beautich.presentation.registration.SignUpViewModel
import com.example.beautich.presentation.search.FilterScreen
import com.example.beautich.presentation.search.SearchViewModel
import com.example.beautich.presentation.service_selection.ServiceSelectionScreen
import com.example.beautich.presentation.service_selection.ServiceSelectionViewModel
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
        composable(
            route = Screen.AppointmentDevelopScreen.route +
                    "?${Constants.APPOINTMENT_ID}={${Constants.APPOINTMENT_ID}}",
            arguments = listOf(navArgument(Constants.APPOINTMENT_ID) {
                type = NavType.StringType
                nullable = true
            })
        ) {
            val viewModel = koinViewModel<AppointmentDevelopViewModel>(
                viewModelStoreOwner = LocalContext.current as ComponentActivity
            )
            val id = it.arguments?.getString(Constants.APPOINTMENT_ID)
            AppointmentDevelopScreen(navController, viewModel, id)
        }
        composable(
            route = Screen.AppointmentDetailsScreen.route +
                    "/{${Constants.APPOINTMENT_ID}}",
            arguments = listOf(navArgument(Constants.APPOINTMENT_ID) { type = NavType.StringType })
        ) {
            val viewModel = koinViewModel<AppointmentDetailsViewModel>()
            AppointmentDetailsScreen(navController, viewModel)
        }
        composable(Screen.FilterScreen.route) {
            val viewModel = koinViewModel<SearchViewModel>(
                viewModelStoreOwner = LocalContext.current as ComponentActivity
            )
            FilterScreen(navController, viewModel)
        }
        composable(
            route = Screen.ServiceSelectionScreen.route +
                    "/{${Constants.FROM_DEVELOP}}",
            arguments = listOf(navArgument(Constants.FROM_DEVELOP) { type = NavType.BoolType })
        ) {
            val viewModel = koinViewModel<ServiceSelectionViewModel>()

            if (it.arguments!!.getBoolean(Constants.FROM_DEVELOP)) {
                val developViewModel = koinViewModel<AppointmentDevelopViewModel>(
                    viewModelStoreOwner = LocalContext.current as ComponentActivity
                )
                ServiceSelectionScreen(navController, viewModel, developViewModel = developViewModel)
            } else {
                val searchViewModel = koinViewModel<SearchViewModel>(
                    viewModelStoreOwner = LocalContext.current as ComponentActivity
                )
                ServiceSelectionScreen(navController, viewModel, searchViewModel = searchViewModel)
            }
        }
    }
}