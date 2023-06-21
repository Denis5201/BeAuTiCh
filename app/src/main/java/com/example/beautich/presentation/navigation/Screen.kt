package com.example.beautich.presentation.navigation

sealed class Screen(val route: String) {
    object StartScreen : Screen("start")
    object SignInScreen : Screen("login")
    object SignUpScreen : Screen("registration")
    object BottomNavigationScreen : Screen("bottom_navigation")
    object SubscriptionScreen : Screen("subscription")
    object MyServicesScreen : Screen("my_services")
    object AppointmentDevelopScreen : Screen("appointment_develop")
    object AppointmentDetailsScreen : Screen("appointment_details")
    object ServiceSelectionScreen : Screen("service_selection")
    object FilterScreen : Screen("filter")
}