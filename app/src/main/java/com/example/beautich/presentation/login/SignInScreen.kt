package com.example.beautich.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.beautich.R
import com.example.beautich.presentation.AppTextField
import com.example.beautich.presentation.ErrorDialog
import com.example.beautich.presentation.WhiteButton
import com.example.beautich.presentation.navigation.Screen

@Composable
fun SignInScreen(navController: NavController, viewModel: SignInViewModel) {

    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.background_with_duck),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.25f))

            Text(
                text = stringResource(R.string.welcome_back),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.padding(20.dp))

            AppTextField(
                input = viewModel.email.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setEmail(it) },
                name = stringResource(R.string.input_email),
                modifier = Modifier.padding(horizontal = 32.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.padding(12.dp))

            AppTextField(
                input = viewModel.password.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setPassword(it) },
                name = stringResource(R.string.input_password),
                modifier = Modifier.padding(horizontal = 32.dp),
                isPassword = true
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WhiteButton(
                text = stringResource(R.string.enter),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, bottom = 16.dp, end = 48.dp)
            ) {
                viewModel.signIn()
            }
            TextButton(
                onClick = { viewModel.navigateToSignUp() },
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text(
                    text = stringResource(R.string.register),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
        }
    }

    if (action is SignInAction.ShowError) {
        ErrorDialog(message = (action as SignInAction.ShowError).message) {
            viewModel.closeErrorDialog()
        }
    }

    LaunchedEffect(action) {
        when(action) {
            SignInAction.NavigateToSignUp -> {
                navController.navigate(Screen.SignUpScreen.route) {
                    popUpTo(navController.graph.id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            SignInAction.NavigateToMain -> {
                navController.navigate(Screen.BottomNavigationScreen.route) {
                    popUpTo(Screen.SignInScreen.route) {
                        inclusive = true
                    }
                }
            }
            else -> {}
        }
    }
}