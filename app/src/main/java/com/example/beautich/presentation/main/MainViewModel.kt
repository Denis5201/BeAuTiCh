package com.example.beautich.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.MessageSource
import com.example.beautich.domain.model.Appointment
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
        getAppointmentsForWeek()
    }

    fun closeErrorDialog() {
        _uiState.value = _uiState.value.copy(
            isErrorDialogOpen = false
        )
    }

    fun loadPreviousWeek() {
        getCurrentDatesForWeek(_uiState.value.currentDays.first().minusWeeks(1L))
        getAppointmentsForWeek()
    }

    fun loadNextWeek() {
        getCurrentDatesForWeek(_uiState.value.currentDays.first().plusWeeks(1L))
        getAppointmentsForWeek()
    }

    fun refresh() {
        getAppointmentsForWeek()
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

    private fun getAppointmentsForWeek() {
        viewModelScope.launch {
            appointmentsRepository.getAppointmentsForTime(
                _uiState.value.currentDays.first().atStartOfDay(),
                _uiState.value.currentDays.last().atTime(23, 59)
            ).collect { result ->
                result.onSuccess {
                    val newList = mutableListOf<List<Appointment>>()
                    for (i in 0..6) {
                        newList.add(it.filter { ap ->
                            ap.startDateTime.toLocalDate() == _uiState.value.currentDays[i]
                        })
                    }
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        appointmentsForWeek = newList
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