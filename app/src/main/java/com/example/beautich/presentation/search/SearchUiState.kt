package com.example.beautich.presentation.search

import com.example.beautich.domain.model.ServiceShort
import java.time.LocalDate

data class SearchUiState(
    val isLoading: Boolean = true,
    val isErrorDialogOpen: Boolean = false,
    val errorMessage: String = "",
    val servicesId: List<ServiceShort> = emptyList(),
    val startPrice: Double? = null,
    val endPrice: Double? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val isDateDialogOpen: Boolean = false,
    val currentDialog: Int = 1
)
