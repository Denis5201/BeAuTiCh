package com.example.beautich.presentation.appointment.details

import com.example.beautich.domain.model.Appointment

data class AppointmentDetailsUiState(
    val isLoading: Boolean = true,
    val isErrorDialogOpen: Boolean = false,
    val errorMessage: String = "",
    val appointment: Appointment? = null
)
