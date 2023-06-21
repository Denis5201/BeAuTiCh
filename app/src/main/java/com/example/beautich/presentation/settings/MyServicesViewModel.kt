package com.example.beautich.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.MessageSource
import com.example.beautich.domain.model.CreateChangeService
import com.example.beautich.domain.model.Service
import com.example.beautich.domain.repository.ServicesRepository
import com.example.beautich.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime

class MyServicesViewModel(
    private val servicesRepository: ServicesRepository,
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyServicesUiState())
    val uiState: StateFlow<MyServicesUiState> = _uiState

    private val _serviceName = MutableStateFlow("")
    val serviceName: StateFlow<String> = _serviceName
    fun setServiceName(value: String) {
        _serviceName.value = value
    }

    private val _servicePrice = MutableStateFlow("")
    val servicePrice: StateFlow<String> = _servicePrice
    fun setServicePrice(value: String) {
        _servicePrice.value = value
    }

    private val _serviceDuration = MutableStateFlow("")
    val serviceDuration: StateFlow<String> = _serviceDuration
    fun setServiceDuration(value: String) {
        _serviceDuration.value = value
    }

    init {
        isSubscribe()
    }

    fun saveService(isChange: Boolean) {
        val price: Double
        val duration: LocalTime
        if (_serviceName.value.isEmpty() || _servicePrice.value.isEmpty() || _serviceDuration.value.isEmpty()) {
            return
        }
        try {
            price = _servicePrice.value.trim().toDouble()
            duration = LocalTime.parse(_serviceDuration.value.trim())
        } catch (e: Exception) {
            return
        }
        viewModelScope.launch {
            if (isChange) {
                servicesRepository.changeService(
                    _uiState.value.currentService!!.id,
                    CreateChangeService(_serviceName.value.trim(), price, duration)
                ).collect { result ->
                    result.onSuccess {
                        _uiState.value = _uiState.value.copy(
                            isServiceDialogOpen = false,
                            currentService = null
                        )
                        getMyServices()
                    }.onFailure {
                        _uiState.value = _uiState.value.copy(
                            isServiceDialogOpen = false,
                            currentService = null,
                            isErrorDialogOpen = true,
                            errorMessage = it.message ?: MessageSource.ERROR
                        )
                    }
                }
            } else {
                servicesRepository.createService(
                    CreateChangeService(_serviceName.value.trim(), price, duration)
                ).collect { result ->
                    result.onSuccess {
                        _uiState.value = _uiState.value.copy(
                            isServiceDialogOpen = false,
                            currentService = null
                        )
                        getMyServices()
                    }.onFailure {
                        _uiState.value = _uiState.value.copy(
                            isServiceDialogOpen = false,
                            currentService = null,
                            isErrorDialogOpen = true,
                            errorMessage = it.message ?: MessageSource.ERROR
                        )
                    }
                }
            }
        }
    }

    fun deleteService() {
        viewModelScope.launch {
            servicesRepository.deleteService(
                _uiState.value.currentService!!.id
            ).collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isMenuDialogOpen = false,
                        currentService = null,
                        servicesList = _uiState.value.servicesList
                            .filter { it.id != _uiState.value.currentService!!.id}
                    )
                }.onFailure {
                    _uiState.value = _uiState.value.copy(
                        isMenuDialogOpen = false,
                        currentService = null,
                        isErrorDialogOpen = true,
                        errorMessage = it.message ?: MessageSource.ERROR
                    )
                }
            }
        }
    }

    fun openMenuDialog(selectedService: Service) {
        _uiState.value = _uiState.value.copy(
            isMenuDialogOpen = true,
            currentService = selectedService
        )
    }

    fun closeMenuDialog() {
        _uiState.value = _uiState.value.copy(
            isMenuDialogOpen = false,
            currentService = null
        )
    }

    fun openServiceDialog(changeService: Service? = null) {
        _uiState.value = _uiState.value.copy(
            isServiceDialogOpen = true,
            isMenuDialogOpen = false
        )
        if (changeService != null) {
            _serviceName.value = changeService.name
            _servicePrice.value = changeService.price.toString()
            _serviceDuration.value = changeService.duration.toString()
        } else {
            _serviceName.value = ""
            _servicePrice.value = ""
            _serviceDuration.value = ""
        }
    }

    fun closeServiceDialog() {
        _uiState.value = _uiState.value.copy(
            isServiceDialogOpen = false,
            currentService = null
        )
    }

    fun closeErrorDialog() {
        _uiState.value = _uiState.value.copy(
            isErrorDialogOpen = false
        )
    }

    fun subscribe() {
        viewModelScope.launch {
            subscriptionRepository.changeSubscribing(true).collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isSubscribing = true
                    )
                    getMyServices()
                }.onFailure {
                    _uiState.value = _uiState.value.copy(
                        isErrorDialogOpen = true,
                        errorMessage = it.message ?: MessageSource.ERROR
                    )
                }
            }
        }
    }

    private fun isSubscribe() {
        viewModelScope.launch {
            subscriptionRepository.isSubscribing().collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isSubscribing = it
                    )
                    if (it) {
                        getMyServices()
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false
                        )
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

    private fun getMyServices() {
        viewModelScope.launch {
            servicesRepository.getCustomServices().collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        servicesList = it
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