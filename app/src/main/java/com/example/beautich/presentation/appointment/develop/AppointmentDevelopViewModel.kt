package com.example.beautich.presentation.appointment.develop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.MessageSource
import com.example.beautich.domain.model.Appointment
import com.example.beautich.domain.model.CreateChangeAppointment
import com.example.beautich.domain.model.ServiceShort
import com.example.beautich.domain.repository.AppointmentsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AppointmentDevelopViewModel(
    private val appointmentsRepository: AppointmentsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppointmentDevelopUiState())
    val uiState: StateFlow<AppointmentDevelopUiState> = _uiState

    private val _action = Channel<AppointmentDevelopAction?>()
    val action = _action.receiveAsFlow()

    private val _clientName = MutableStateFlow("")
    val clientName: StateFlow<String> = _clientName
    fun setClientName(value: String) {
        _clientName.value = value
    }

    private val _dateTime = MutableStateFlow("")
    val dateTime: StateFlow<String> = _dateTime

    private val _clientPhone = MutableStateFlow("")
    val clientPhone: StateFlow<String> = _clientPhone
    fun setClientPhone(value: String) {
        _clientPhone.value = value
    }

    var appointmentId: String? = null
        private set
    private var appointment: Appointment? = null
    private var date: LocalDate = LocalDate.now()
    private var time: LocalTime = LocalTime.MIN
    fun setAppointmentId(id: String) {
        appointmentId = id
        getAppointment(id)
    }

    fun addService(service: ServiceShort) {
        val newList = _uiState.value.servicesId.toMutableList()
        newList.add(service)
        _uiState.value = _uiState.value.copy(
            servicesId = newList
        )
    }

    fun deleteService(service: ServiceShort) {
        val newList = _uiState.value.servicesId.toMutableList()
        newList.remove(service)
        _uiState.value = _uiState.value.copy(
            servicesId = newList
        )
    }

    fun openDateDialog() {
        _uiState.value = _uiState.value.copy(
            isDateDialogOpen = true
        )
    }

    fun closeDateDialog() {
        _uiState.value = _uiState.value.copy(
            isDateDialogOpen = false
        )
    }

    fun setDate(newDate: LocalDate) {
        date = newDate
        _dateTime.value = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        _uiState.value = _uiState.value.copy(
            isDateDialogOpen = false,
            isTimeDialogOpen = true
        )
    }

    fun setTime(hour: Int, minute: Int) {
        time = LocalTime.of(hour, minute)
        _dateTime.value = _dateTime.value + " " +
                time.format(DateTimeFormatter.ofPattern("HH:mm"))

        _uiState.value = _uiState.value.copy(
            isTimeDialogOpen = false
        )
    }

    fun closeErrorDialog() {
        _uiState.value = _uiState.value.copy(
            isErrorDialogOpen = false
        )
    }

    fun createNewAppointment() {
        if (!isValidInput()) {
            return
        }
        viewModelScope.launch {
            appointmentsRepository.createAppointment(
                CreateChangeAppointment(
                    _clientName.value.trim(), _clientPhone.value.trim().ifEmpty { null },
                    date.atTime(time), _uiState.value.servicesId.map { it.id }
                )
            ).collect { result ->
                result.onSuccess {
                    _action.send(AppointmentDevelopAction.NavigateBack)
                }.onFailure {
                    _uiState.value = _uiState.value.copy(
                        isErrorDialogOpen = true,
                        errorMessage = it.message ?: MessageSource.ERROR
                    )
                }
            }
        }
    }

    fun changeAppointment() {
        if (!isValidInput()) {
            return
        }
        viewModelScope.launch {
            appointmentsRepository.changeAppointment(
                appointmentId!!, CreateChangeAppointment(
                    _clientName.value.trim(), _clientPhone.value.trim().ifEmpty { null },
                    date.atTime(time), _uiState.value.servicesId.map { it.id }
                )
            ).collect { result ->
                result.onSuccess {
                    _action.send(AppointmentDevelopAction.NavigateBack)
                }.onFailure {
                    _uiState.value = _uiState.value.copy(
                        isErrorDialogOpen = true,
                        errorMessage = it.message ?: MessageSource.ERROR
                    )
                }
            }
        }
    }

    private fun getAppointment(appointmentId: String) {
        viewModelScope.launch {
            appointmentsRepository.getAppointment(appointmentId).collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        servicesId = it.services
                    )
                    appointment = it
                    _clientName.value = it.clientName
                    _dateTime.value = it.startDateTime.format(
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                    )
                    _clientPhone.value = it.clientPhone ?: ""
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

    private fun isValidInput(): Boolean {
        if (_clientName.value.trim().isEmpty() || _dateTime.value.isEmpty()) {
            return false
        }
        if (_uiState.value.servicesId.isEmpty()) {
            return false
        }
        return true
    }
}