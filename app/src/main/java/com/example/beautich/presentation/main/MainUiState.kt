package com.example.beautich.presentation.main

import com.example.beautich.domain.model.Appointment
import java.time.LocalDate

data class MainUiState(
    val isLoading: Boolean = true,
    val isErrorDialogOpen: Boolean = false,
    val errorMessage: String = "",
    val currentDay: LocalDate = LocalDate.now(),
    val currentDays: List<LocalDate> = emptyList(),
    val appointmentsForWeek: List<Appointment> = emptyList()
)
