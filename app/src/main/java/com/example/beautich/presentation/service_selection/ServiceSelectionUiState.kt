package com.example.beautich.presentation.service_selection

import com.example.beautich.domain.model.ServiceShort

data class ServiceSelectionUiState(
    val isLoading: Boolean = true,
    val isErrorDialogOpen: Boolean = false,
    val errorMessage: String = "",
    val services: List<Pair<ServiceShort, Boolean>> = emptyList()
)
