package com.example.beautich.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.MessageSource
import com.example.beautich.domain.model.Appointment
import com.example.beautich.domain.model.ServiceShort
import com.example.beautich.domain.repository.AppointmentsRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val appointmentsRepository: AppointmentsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private val _search = MutableStateFlow("")
    val search: StateFlow<String> = _search
    fun setSearch(value: String) {
        _search.value = value
    }

    private val _appointments = MutableStateFlow(listOf<Appointment>())
    val appointments = search
        .debounce(500)
        .combine(_appointments) { text, list ->
            if (text.isEmpty()) {
                list
            } else {
                list.filter { it.clientName.contains(text, true) }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _appointments.value
        )

    private val _startPrice = MutableStateFlow("")
    val startPrice: StateFlow<String> = _startPrice
    fun setStartPrice(value: String) {
        _startPrice.value = value
        _uiState.value = _uiState.value.copy(
            startPrice = if (_startPrice.value.isEmpty()) {
                null
            } else {
                try { _startPrice.value.toDouble() } catch (_: Exception) { null }
            }
        )
    }

    private val _endPrice = MutableStateFlow("")
    val endPrice: StateFlow<String> = _endPrice
    fun setEndPrice(value: String) {
        _endPrice.value = value
        _uiState.value = _uiState.value.copy(
            endPrice = if (_endPrice.value.isEmpty()) {
                null
            } else {
                try { _endPrice.value.toDouble() } catch (_: Exception) { null }
            }
        )
    }

    private val _startDate = MutableStateFlow("")
    val startDate: StateFlow<String> = _startDate

    private val _endDate = MutableStateFlow("")
    val endDate: StateFlow<String> = _endDate

    init {
        getAppointmentWithFilter()
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

    fun getAppointmentWithFilter() {
        viewModelScope.launch {
            appointmentsRepository.getAppointmentsForFilters(
                _uiState.value.startPrice, _uiState.value.endPrice,
                _uiState.value.startDate?.atTime(LocalTime.MIN), _uiState.value.endDate?.atTime(23, 59),
                _uiState.value.servicesId.map { it.id }.toTypedArray()
            ).collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                    _appointments.value = it
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

    fun cleanAllFilters() {
        _uiState.value = _uiState.value.copy(
            startPrice = null,
            endPrice = null,
            startDate = null,
            endDate = null,
            servicesId = emptyList()
        )
        _startPrice.value = ""
        _endPrice.value = ""
        _startDate.value = ""
        _endDate.value = ""
    }

    fun openDateDialog(which: Int) {
        _uiState.value = _uiState.value.copy(
            isDateDialogOpen = true,
            currentDialog = which
        )
    }

    fun closeDateDialog() {
        _uiState.value = _uiState.value.copy(
            isDateDialogOpen = false
        )
    }

    fun setDate(newDate: LocalDate) {

        if (_uiState.value.currentDialog == 1) {
            _uiState.value = _uiState.value.copy(
                startDate = newDate,
                isDateDialogOpen = false
            )
            _startDate.value = newDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        } else {
            _uiState.value = _uiState.value.copy(
                endDate = newDate,
                isDateDialogOpen = false
            )
            _endDate.value = newDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        }
    }

    fun closeErrorDialog() {
        _uiState.value = _uiState.value.copy(
            isErrorDialogOpen = false
        )
    }
}