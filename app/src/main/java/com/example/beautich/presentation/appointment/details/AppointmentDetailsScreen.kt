package com.example.beautich.presentation.appointment.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.beautich.Constants
import com.example.beautich.R
import com.example.beautich.domain.model.StatusAppointment
import com.example.beautich.presentation.ErrorDialog
import com.example.beautich.presentation.WhiteButton
import com.example.beautich.presentation.navigation.Screen
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppointmentDetailsScreen(
    navController: NavController,
    viewModel: AppointmentDetailsViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

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
                text = uiState.appointment?.clientName ?: "",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        if (uiState.appointment == null) {
            return@Column
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.services),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Spacer(modifier = Modifier.padding(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                uiState.appointment!!.services.forEach {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .background(
                                Color.Transparent,
                                RoundedCornerShape(7.dp)
                            )
                            .border(
                                width = 0.3.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(7.dp)
                            )
                            .padding(5.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

            }

            Spacer(modifier = Modifier.padding(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.start_time),
                    modifier = Modifier.fillMaxWidth(0.5f),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )

                Text(
                    text = uiState.appointment!!.startDateTime
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W400),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.end_time),
                    modifier = Modifier.fillMaxWidth(0.5f),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )

                Text(
                    text = uiState.appointment!!.endDateTime
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W400),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.phone),
                    modifier = Modifier.fillMaxWidth(0.5f),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )

                Text(
                    text = uiState.appointment!!.clientPhone ?: "",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W400),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            when (uiState.appointment!!.status) {
                StatusAppointment.New -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.client_has_been),
                            modifier = Modifier
                                .width(100.dp)
                                .background(
                                    MaterialTheme.colorScheme.tertiary,
                                    RoundedCornerShape(topStart = 40.dp, bottomStart = 40.dp)
                                )
                                .padding(vertical = 4.dp)
                                .clickable { viewModel.changeStatus(StatusAppointment.Completed) },
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(R.string.cancel_appointment),
                            modifier = Modifier
                                .width(100.dp)
                                .background(
                                    MaterialTheme.colorScheme.secondaryContainer,
                                    RoundedCornerShape(topEnd = 40.dp, bottomEnd = 40.dp)
                                )
                                .padding(vertical = 4.dp)
                                .clickable { viewModel.changeStatus(StatusAppointment.Cancelled) },
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                StatusAppointment.Completed -> {
                    Text(
                        text = stringResource(R.string.appointment_done),
                        modifier = Modifier
                            .width(120.dp)
                            .align(Alignment.CenterHorizontally)
                            .background(
                                MaterialTheme.colorScheme.tertiary,
                                RoundedCornerShape(40.dp)
                            )
                            .padding(vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }
                StatusAppointment.Cancelled -> {
                    Text(
                        text = stringResource(R.string.appointment_canceled),
                        modifier = Modifier
                            .width(120.dp)
                            .align(Alignment.CenterHorizontally)
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer,
                                RoundedCornerShape(40.dp)
                            )
                            .padding(vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Spacer(modifier = Modifier.weight(1f))

            if (uiState.appointment!!.status == StatusAppointment.New) {
                WhiteButton(
                    text = stringResource(R.string.change_data),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                ) {
                    navController.navigate(Screen.AppointmentDevelopScreen.route +
                            "?${Constants.APPOINTMENT_ID}=${viewModel.appointmentId}"
                    )
                }
            }
            TextButton(
                onClick = { viewModel.deleteAppointment() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    text = stringResource(R.string.delete),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
        }
    }

    if (uiState.isErrorDialogOpen) {
        ErrorDialog(message = uiState.errorMessage) {
            viewModel.closeErrorDialog()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getAppointment()
    }

    LaunchedEffect(key1 = action) {
        when (action) {
            AppointmentDetailsAction.NavigateBack -> {
                navController.navigateUp()
            }
            else -> {}
        }
    }
}