package com.example.beautich.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.MessageSource
import com.example.beautich.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SubscriptionViewModel(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionUiState())
    val uiState: StateFlow<SubscriptionUiState> = _uiState

    init {
        isSubscribe()
    }

    fun changeSubscribeStatus(isSubscribing: Boolean) {
        viewModelScope.launch {
            subscriptionRepository.changeSubscribing(isSubscribing).collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isSubscribing = isSubscribing
                    )
                    if (isSubscribing) {
                        _uiState.value = _uiState.value.copy(
                            subscriptionDate = LocalDateTime.now()
                        )
                    }
                }.onFailure {
                    _uiState.value = _uiState.value.copy(
                        isErrorDialogOpen = true,
                        errorMessage = it.message ?: MessageSource.ERROR
                    )
                }
            }
        }
    }

    fun closeErrorDialog() {
        _uiState.value = _uiState.value.copy(
            isErrorDialogOpen = false
        )
    }

    private fun isSubscribe() {
        viewModelScope.launch {
            subscriptionRepository.isSubscribing().collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isSubscribing = it
                    )
                    if (it) {
                        getSubscriptionInfo()
                    }
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

    private fun getSubscriptionInfo() {
        viewModelScope.launch {
            subscriptionRepository.getSubscribingDateTime().collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        subscriptionDate = it
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