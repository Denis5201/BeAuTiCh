package com.example.beautich.presentation.appointment.develop

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.beautich.R
import com.example.beautich.presentation.AppTextField
import com.example.beautich.presentation.DateTimeTextField
import com.example.beautich.presentation.ErrorDialog
import com.example.beautich.presentation.WhiteButton
import com.example.beautich.presentation.navigation.Screen
import com.example.beautich.ui.theme.Gray
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentDevelopScreen(
    navController: NavController,
    viewModel: AppointmentDevelopViewModel,
    id: String? = null
) {

    if (id != null && viewModel.appointmentId == null) {
        viewModel.setAppointmentId(id)
    }

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
                text = if (viewModel.appointmentId.isNullOrEmpty())
                    stringResource(R.string.add_appointment)
                else
                    stringResource(R.string.change_appointment),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.write_client_name),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Spacer(modifier = Modifier.padding(5.dp))

            AppTextField(
                input = viewModel.clientName.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setClientName(it) },
                name = stringResource(R.string.input_name),
                modifier = Modifier
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = stringResource(R.string.choose_datetime_appointment),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            
            Spacer(modifier = Modifier.padding(5.dp))
            
            DateTimeTextField(input = viewModel.dateTime.collectAsStateWithLifecycle().value) {
                viewModel.openDateDialog()
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = stringResource(R.string.write_client_phone),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Spacer(modifier = Modifier.padding(5.dp))

            AppTextField(
                input = viewModel.clientPhone.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setClientPhone(it) },
                name = stringResource(R.string.input_phone),
                modifier = Modifier,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.padding(10.dp))

            WhiteButton(
                text = stringResource(R.string.choose_services),
                modifier = Modifier.align(Alignment.End)
            ) {
                navController.navigate(
                    Screen.ServiceSelectionScreen.route + "/true"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            WhiteButton(
                text = stringResource(R.string.save),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 32.dp)
            ) {
                if (viewModel.appointmentId.isNullOrEmpty()) {
                    viewModel.createNewAppointment()
                } else {
                    viewModel.changeAppointment()
                }
            }
        }
    }

    if (uiState.isErrorDialogOpen) {
        ErrorDialog(message = uiState.errorMessage) {
            viewModel.closeErrorDialog()
        }
    }

    if (uiState.isDateDialogOpen) {
        val dateState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { viewModel.closeDateDialog() },
            confirmButton = {
                TextButton(
                    onClick = {
                        dateState.selectedDateMillis?.let {
                            viewModel.setDate(Instant.ofEpochMilli(it)
                                .atZone(ZoneId.systemDefault()).toLocalDate())
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.choose)
                    )
                }
            },
            colors = DatePickerDefaults.colors(containerColor = Gray)
        ) {
            DatePicker(state = dateState)
        }
    }
    if (uiState.isTimeDialogOpen) {
        val timeState = rememberTimePickerState()

        TimePickerDialog(
            onConfirm = {
                viewModel.setTime(timeState.hour, timeState.minute)
            }
        ) {
            TimePicker(
                state = timeState,
                colors = TimePickerDefaults.colors(containerColor = Gray)
            )
        }
    }

    LaunchedEffect(action) {
        when(action) {
            AppointmentDevelopAction.NavigateBack -> {
                navController.navigateUp()
            }
            AppointmentDevelopAction.NavigateToServiceSelection -> {
                navController.navigate(
                    Screen.ServiceSelectionScreen.route + "/true"
                )
            }
            else -> {}
        }
    }
}