package com.example.beautich.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.beautich.R
import com.example.beautich.presentation.ErrorDialog
import com.example.beautich.presentation.WhiteButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyServicesScreen(navController: NavController, viewModel: MyServicesViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.background2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        navController.navigateUp()
                    }
            )

            Spacer(modifier = Modifier.padding(start = 16.dp))

            Text(
                text = stringResource(R.string.your_services),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            if (uiState.isSubscribing) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.plus),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable {
                                viewModel.openServiceDialog()
                            }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        if (uiState.isLoading) {
            return@Column
        }

        if (uiState.isSubscribing) {
            if (uiState.servicesList.isEmpty()) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.empty_services_list),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.padding(4.dp))

                Text(
                    text = stringResource(R.string.empty_services_list),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            } else {
                FlowRow(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.servicesList.forEach {
                        Text(
                            text = it.name,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
                                .pointerInput(it) {
                                    detectTapGestures(
                                        onLongPress = { _ ->
                                            viewModel.openMenuDialog(it)
                                        }
                                    )
                                }
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }

                if(uiState.isMenuDialogOpen) {
                    MenuDialog(uiState = uiState, viewModel = viewModel)
                }
            }

            if (uiState.isServiceDialogOpen) {
                ServiceDialog(uiState = uiState, viewModel = viewModel)
            }
        } else {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.no_subscription),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.padding(12.dp))

            Text(
                text = stringResource(R.string.no_subscription_for_services),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = Color.White
            )

            Spacer(modifier = Modifier.padding(16.dp))

            WhiteButton(
                text = stringResource(R.string.subscribe),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                viewModel.subscribe()
            }
        }
    }

    if (uiState.isErrorDialogOpen) {
        ErrorDialog(message = uiState.errorMessage) {
            viewModel.closeErrorDialog()
        }
    }
}