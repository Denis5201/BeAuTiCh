package com.example.beautich.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
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
import java.time.format.DateTimeFormatter

@Composable
fun SubscriptionScreen(navController: NavController, viewModel: SubscriptionViewModel) {

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
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
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
                text = stringResource(R.string.info_about_subscription),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.padding(20.dp))

        if (uiState.isSubscribing) {
            Text(
                text = stringResource(
                    R.string.subscription_date,
                    uiState.subscriptionDate
                        ?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) ?: ""
                ),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Box(
                modifier = Modifier.fillMaxSize().padding(bottom = 32.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                WhiteButton(
                    text = stringResource(R.string.unsubscribe),
                    modifier = Modifier
                ) {
                    viewModel.changeSubscribeStatus(false)
                }
            }
        } else {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.no_subscription),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.padding(12.dp))

            Text(
                text = stringResource(R.string.no_subscription),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = Color.White
            )

            Spacer(modifier = Modifier.padding(16.dp))
            
            WhiteButton(text = stringResource(R.string.subscribe),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                viewModel.changeSubscribeStatus(true)
            }
        }
    }

    if (uiState.isErrorDialogOpen) {
        ErrorDialog(message = uiState.errorMessage) {
            viewModel.closeErrorDialog()
        }
    }
}