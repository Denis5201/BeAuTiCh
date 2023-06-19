package com.example.beautich.presentation.settings

import com.example.beautich.domain.model.Service

data class MyServicesUiState(
    val isLoading: Boolean = true,
    val isSubscribing: Boolean = false,
    val isErrorDialogOpen: Boolean = false,
    val isMenuDialogOpen: Boolean = false,
    val isServiceDialogOpen: Boolean = false,
    val currentService: Service? = null,
    val errorMessage: String = "",
    val servicesList: List<Service> = emptyList()
)
