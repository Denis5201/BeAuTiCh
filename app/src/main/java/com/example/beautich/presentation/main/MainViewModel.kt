package com.example.beautich.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.MessageSource
import com.example.beautich.domain.repository.AppointmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

class MainViewModel(
    private val appointmentsRepository: AppointmentsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        getCurrentDatesForWeek(_uiState.value.currentDay)
        getAppointmentsForWeek(_uiState.value.currentDays.first(), _uiState.value.currentDays.last())
    }

    fun closeErrorDialog() {
        _uiState.value = _uiState.value.copy(
            isErrorDialogOpen = false
        )
    }

    private fun getCurrentDatesForWeek(currentDate: LocalDate) {
        val newCurrentDays = mutableListOf<LocalDate>()

        val currentDay = currentDate.dayOfWeek.value
        val startDay = currentDate.minusDays(currentDay.toLong() - DayOfWeek.MONDAY.value)

        for (i in 0..6) {
            newCurrentDays.add(startDay.plusDays(i.toLong()))
        }

        _uiState.value = _uiState.value.copy(
            currentDays = newCurrentDays
        )
    }

    private fun getAppointmentsForWeek(startDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch {
            appointmentsRepository.getAppointmentsForTime(
                startDate.atStartOfDay(), endDate.atTime(23, 59)
            ).collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        appointmentsForWeek = it
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
}