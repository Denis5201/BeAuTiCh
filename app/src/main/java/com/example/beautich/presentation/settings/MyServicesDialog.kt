package com.example.beautich.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.beautich.R
import com.example.beautich.presentation.DialogTextField
import com.example.beautich.ui.theme.Gray

@Composable
fun MenuDialog(
    uiState: MyServicesUiState,
    viewModel: MyServicesViewModel
) {
    Dialog(onDismissRequest = { viewModel.closeMenuDialog() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray, RoundedCornerShape(10.dp))
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.choose_action),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.padding(5.dp))

            Text(
                text = stringResource(R.string.menu_dialog_hint, uiState.currentService!!.name),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    modifier = Modifier
                        .clickable { viewModel.closeMenuDialog() },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primaryContainer
                )

                Spacer(modifier = Modifier.padding(start = 16.dp))

                Text(
                    text = stringResource(R.string.delete),
                    modifier = Modifier
                        .clickable { viewModel.deleteService() },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.padding(start = 16.dp))

                Text(
                    text = stringResource(R.string.change),
                    modifier = Modifier
                        .clickable { viewModel.openServiceDialog(uiState.currentService) },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun ServiceDialog(
    uiState: MyServicesUiState,
    viewModel: MyServicesViewModel
) {
    Dialog(onDismissRequest = { viewModel.closeServiceDialog() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray, RoundedCornerShape(10.dp))
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(
                    if (uiState.currentService != null) R.string.change_service
                    else R.string.add_service
                ),
                style = MaterialTheme.typography.titleSmall.copy(
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.padding(5.dp))

            Text(
                text = stringResource(R.string.service_dialog_hint),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.padding(5.dp))

            DialogTextField(input = viewModel.serviceName.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setServiceName(it) },
                name = stringResource(R.string.input_service_name)
            )

            Spacer(modifier = Modifier.padding(4.dp))

            DialogTextField(input = viewModel.servicePrice.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setServicePrice(it) },
                name = stringResource(R.string.input_service_price),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Spacer(modifier = Modifier.padding(4.dp))

            DialogTextField(input = viewModel.serviceDuration.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setServiceDuration(it) },
                name = stringResource(R.string.input_service_duration),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii)
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    modifier = Modifier
                        .clickable { viewModel.closeServiceDialog() },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primaryContainer
                )

                Spacer(modifier = Modifier.padding(start = 16.dp))

                Text(
                    text = stringResource(R.string.save),
                    modifier = Modifier
                        .clickable { viewModel.saveService(uiState.currentService != null) },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}