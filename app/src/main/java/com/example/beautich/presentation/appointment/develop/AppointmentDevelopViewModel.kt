package com.example.beautich.presentation.appointment.develop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.beautich.domain.repository.AppointmentsRepository

class AppointmentDevelopViewModel(
    private val appointmentsRepository: AppointmentsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

}