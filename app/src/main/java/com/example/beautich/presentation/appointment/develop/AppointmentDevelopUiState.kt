package com.example.beautich.presentation.appointment.develop

import com.example.beautich.domain.model.ServiceShort

data class AppointmentDevelopUiState(
    val isLoading: Boolean = true,
    val isErrorDialogOpen: Boolean = false,
    val errorMessage: String = "",
    val isDateDialogOpen: Boolean = false,
    val isTimeDialogOpen: Boolean = false,
    val servicesId: List<ServiceShort> = emptyList()
)
