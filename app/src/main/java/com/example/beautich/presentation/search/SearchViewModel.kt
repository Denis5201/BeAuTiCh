package com.example.beautich.presentation.search

import androidx.lifecycle.ViewModel
import com.example.beautich.domain.model.ServiceShort
import com.example.beautich.domain.repository.AppointmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(
    private val appointmentsRepository: AppointmentsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private val _startPrice = MutableStateFlow("")
    val startPrice: StateFlow<String> = _startPrice
    fun setStartPrice(value: String) {
        _startPrice.value = value
    }

    private val _endPrice = MutableStateFlow("")
    val endPrice: StateFlow<String> = _endPrice
    fun setEndPrice(value: String) {
        _endPrice.value = value
    }

    init {

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
}