package com.example.beautich.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.beautich.R
import com.example.beautich.presentation.ErrorDialog
import com.example.beautich.presentation.WhiteButton
import com.example.beautich.presentation.navigation.Screen

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
    ) {
        if (uiState.avatar == null) {
            Image(
                imageVector = ImageVector.vectorResource(
                    R.drawable.default_avatar
                ),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Image(
                bitmap = uiState.avatar!!.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopCenter),
                contentScale = ContentScale.FillWidth
            )
        }

        Text(
            text = stringResource(R.string.exit),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(end = 16.dp)
                .clickable {
                    viewModel.exit()
                },
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )

        if (uiState.profile?.avatar == false) {
            Text(
                text = stringResource(R.string.change_avatar),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp)
                    .clickable {
                        viewModel.openPhotoDialog()
                    },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
        if (uiState.profile?.avatar == true) {
            Text(
                text = stringResource(R.string.delete_avatar),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 16.dp)
                    .clickable {
                        viewModel.deletePhoto()
                    },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }

    uiState.profile?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.25f))

            Text(
                text = it.fullName,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            
            Spacer(modifier = Modifier.padding(10.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.phone),
                    modifier = Modifier.fillMaxWidth(0.3f),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                Text(
                    text = it.phoneNumber ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.padding(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.email),
                    modifier = Modifier.fillMaxWidth(0.3f),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                Text(
                    text = it.email,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }
        }

        AvatarLoader(uiState = uiState, uploadNewPhoto = { viewModel.uploadNewPhoto(it) },
            closePhotoDialog = { viewModel.closePhotoDialog() }, setUri = { viewModel.setUri(it) }
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            WhiteButton(
                text = stringResource(R.string.change_profile_data),
                modifier = Modifier
                    .width(240.dp)
                    .padding(bottom = 20.dp)
            ) {
                viewModel.openChangeProfileDialog()
            }
            TextButton(
                onClick = {  },
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text(
                    text = stringResource(R.string.change_password),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
            }
        }

        if (uiState.isChangeProfileDialogOpen) {
            ChangeProfileDataDialog(viewModel = viewModel)
        }
    }


    if (action is ProfileAction.ShowError) {
        ErrorDialog(message = (action as ProfileAction.ShowError).message) {
            viewModel.closeErrorDialog()
        }
    }

    LaunchedEffect(action) {
        when(action) {
            ProfileAction.Logout -> {
                navController.navigate(Screen.SignInScreen.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
            else -> {}
        }
    }
}