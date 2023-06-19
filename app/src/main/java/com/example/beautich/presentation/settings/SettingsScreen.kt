package com.example.beautich.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.beautich.R
import com.example.beautich.presentation.navigation.Screen

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.watch_my_services),
                modifier = Modifier.clickable {
                    navController.navigate(Screen.MyServicesScreen.route)
                },
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_right),
                contentDescription = null,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.MyServicesScreen.route)
                }
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.info_about_subscription),
                modifier = Modifier.clickable {
                    navController.navigate(Screen.SubscriptionScreen.route)
                },
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_right),
                contentDescription = null,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.SubscriptionScreen.route)
                }
            )
        }
    }
}