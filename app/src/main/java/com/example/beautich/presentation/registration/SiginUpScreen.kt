package com.example.beautich.presentation.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel) {

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
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.3f))
            Text(
                text = stringResource(R.string.welcome),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 32.dp)
            ) {
                AppTextField(
                    input = viewModel.surname.collectAsStateWithLifecycle().value,
                    valChange = { viewModel.setSurname(it) },
                    name = stringResource(R.string.input_surname),
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.padding(12.dp))

                AppTextField(
                    input = viewModel.name.collectAsStateWithLifecycle().value,
                    valChange = { viewModel.setName(it) },
                    name = stringResource(R.string.input_name),
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.padding(12.dp))

                AppTextField(
                    input = viewModel.patronymic.collectAsStateWithLifecycle().value,
                    valChange = { viewModel.setPatronymic(it) },
                    name = stringResource(R.string.input_patronymic),
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.padding(12.dp))

                AppTextField(
                    input = viewModel.email.collectAsStateWithLifecycle().value,
                    valChange = { viewModel.setEmail(it) },
                    name = "${stringResource(R.string.input_email)}*",
                    modifier = Modifier,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.padding(12.dp))

                AppTextField(
                    input = viewModel.password.collectAsStateWithLifecycle().value,
                    valChange = { viewModel.setPassword(it) },
                    name = "${stringResource(R.string.input_password)}*",
                    modifier = Modifier,
                    isPassword = true
                )

                Spacer(modifier = Modifier.padding(12.dp))

                AppTextField(
                    input = viewModel.confirm.collectAsStateWithLifecycle().value,
                    valChange = { viewModel.setConfirm(it) },
                    name = stringResource(R.string.input_confirm_password),
                    modifier = Modifier,
                    isPassword = true
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WhiteButton(
                text = stringResource(R.string.register),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, bottom = 16.dp, end = 48.dp)
            ) {
                viewModel.signUp()
            }
            TextButton(
                onClick = { viewModel.navigateToSignIn() },
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text(
                    text = stringResource(R.string.already_have_account),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
        }
    }

    if (action is SignUpAction.ShowError) {
        ErrorDialog(message = (action as SignUpAction.ShowError).message) {
            viewModel.closeErrorDialog()
        }
    }

    LaunchedEffect(action) {
        when(action) {
            SignUpAction.NavigateToSignIn -> {
                navController.navigate(Screen.SignInScreen.route) {
                    popUpTo(navController.graph.id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            SignUpAction.NavigateToMain -> {
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