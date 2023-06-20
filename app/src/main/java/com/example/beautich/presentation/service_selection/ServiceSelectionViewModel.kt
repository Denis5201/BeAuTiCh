package com.example.beautich.presentation.service_selection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.Constants
import com.example.beautich.MessageSource
import com.example.beautich.domain.model.ServiceShort
import com.example.beautich.domain.repository.ServicesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServiceSelectionViewModel(
    private val servicesRepository: ServicesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServiceSelectionUiState())
    val uiState: StateFlow<ServiceSelectionUiState> = _uiState

    val fromDevelop: Boolean
    init {
        fromDevelop = checkNotNull(savedStateHandle[Constants.FROM_DEVELOP])
        getServices()
    }

    fun setAlreadyChosen(servicesList: List<ServiceShort>) {
        val newList = mutableListOf<Pair<ServiceShort, Boolean>>()
        _uiState.value.services.forEach { pair ->
            if (servicesList.any { it.id == pair.first.id }) {
                newList.add(pair.copy(second = true))
            } else {
                newList.add(pair)
            }
        }
    }

    fun changeSelection(id: String) {
        val newList = _uiState.value.services.toMutableList()
        _uiState.value.services.withIndex().firstOrNull { it.value.first.id == id }?.let {
            newList[it.index] = it.value.copy(it.value.first, !it.value.second)
        }
        _uiState.value = _uiState.value.copy(
            services = newList
        )
    }

    private fun getServices() {
        viewModelScope.launch {
            servicesRepository.getAllServices().collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        services = it.map {
                                service -> Pair(ServiceShort(service.id, service.name), false)
                        }
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