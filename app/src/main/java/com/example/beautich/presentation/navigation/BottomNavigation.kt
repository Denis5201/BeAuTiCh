package com.example.beautich.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.beautich.R
import com.example.beautich.presentation.profile.ProfileScreen

@Composable
fun BottomNavigation(navController: NavController) {
    val bottomNavController = rememberNavController()

    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            val items = listOf(
                BottomNavScreen.Main, BottomNavScreen.Search,
                BottomNavScreen.Profile, BottomNavScreen.Settings
            )
            NavigationBar(
                containerColor = Color.Transparent
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Box(
                                modifier = Modifier
                                    .height(75.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                if (currentDestination?.hierarchy
                                        ?.any { it.route == screen.route } == true
                                ) {
                                    Image(
                                        imageVector = ImageVector.vectorResource(R.drawable.selected_nav_item),
                                        contentDescription = null
                                    )
                                }
                                Image(
                                    imageVector = ImageVector.vectorResource(screen.icon),
                                    contentDescription = null,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }

                        },
                        selected = false,
                        onClick = {
                            bottomNavController.navigate(screen.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                bitmap = if (currentDestination?.hierarchy
                        ?.any { it.route == BottomNavScreen.Profile.route } == true
                ) {
                    ImageBitmap.imageResource(R.drawable.background_without_duck)
                } else {
                    ImageBitmap.imageResource(R.drawable.background2)
                },
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }

        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavScreen.Main.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.Main.route) {

            }
            composable(BottomNavScreen.Search.route) {

            }
            composable(BottomNavScreen.Profile.route) {
                ProfileScreen(navController = navController)
            }
            composable(BottomNavScreen.Settings.route) {

            }
        }
    }
}