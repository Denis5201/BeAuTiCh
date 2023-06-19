package com.example.beautich.presentation.settings

import java.time.LocalDateTime

data class SubscriptionUiState(
    val isLoading: Boolean = true,
    val isErrorDialogOpen: Boolean = false,
    val errorMessage: String = "",
    val isSubscribing: Boolean = false,
    val subscriptionDate: LocalDateTime? = null
)
