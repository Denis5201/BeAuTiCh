package com.example.beautich.presentation.navigation

import com.example.beautich.R

sealed class BottomNavScreen(val route: String, val icon: Int) {
    object Main : BottomNavScreen("main", R.drawable.home)
    object Search : BottomNavScreen("search", R.drawable.search)
    object Profile : BottomNavScreen("profile", R.drawable.profile)
    object Settings : BottomNavScreen("settings", R.drawable.setting)
}
