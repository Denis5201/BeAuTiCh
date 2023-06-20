package com.example.beautich.presentation.appointment.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.beautich.domain.repository.AppointmentsRepository

class AppointmentDetailsViewModel(
    private val appointmentsRepository: AppointmentsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

}