package com.example.beautich.presentation.appointment.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.Constants
import com.example.beautich.MessageSource
import com.example.beautich.domain.model.StatusAppointment
import com.example.beautich.domain.repository.AppointmentsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AppointmentDetailsViewModel(
    private val appointmentsRepository: AppointmentsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppointmentDetailsUiState())
    val uiState: StateFlow<AppointmentDetailsUiState> = _uiState

    private val _action = Channel<AppointmentDetailsAction?>()
    val action = _action.receiveAsFlow()

    val appointmentId: String
    init {
        appointmentId = checkNotNull(savedStateHandle[Constants.APPOINTMENT_ID])
    }

    fun changeStatus(statusAppointment: StatusAppointment) {
        viewModelScope.launch {
            appointmentsRepository
                .changeAppointmentStatus(appointmentId, statusAppointment).collect { result ->
                    result.onSuccess {
                        _uiState.value = _uiState.value.copy(
                            appointment = _uiState.value.appointment?.copy(status = statusAppointment)
                        )
                    }.onFailure {
                        _uiState.value = _uiState.value.copy(
                            isErrorDialogOpen = true,
                            errorMessage = it.message ?: MessageSource.ERROR
                        )
                    }
            }
        }
    }

    fun deleteAppointment() {
        viewModelScope.launch {
            appointmentsRepository.deleteAppointment(appointmentId).collect { result ->
                result.onSuccess {
                    _action.send(AppointmentDetailsAction.NavigateBack)
                }.onFailure {
                    _uiState.value = _uiState.value.copy(
                        isErrorDialogOpen = true,
                        errorMessage = it.message ?: MessageSource.ERROR
                    )
                }
            }
        }
    }

    fun getAppointment() {
        viewModelScope.launch {
            appointmentsRepository.getAppointment(appointmentId).collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        appointment = it
                    )
                }.onFailure {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isErrorDialogOpen = true,
                        errorMessage = it.message ?: MessageSource.ERROR
                    )
                }
            }
        }
    }

    fun closeErrorDialog() {
        _uiState.value = _uiState.value.copy(
            isErrorDialogOpen = false
        )
    }
}