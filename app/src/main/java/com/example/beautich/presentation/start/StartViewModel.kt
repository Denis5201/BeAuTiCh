package com.example.beautich.presentation.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.domain.repository.LocalSettings
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class StartViewModel(
    localSettings: LocalSettings
) : ViewModel() {

    private val _action = Channel<StartAction?>()
    val action = _action.receiveAsFlow()

    init {
        if (!localSettings.getFirstRunApp()) {
            viewModelScope.launch {
                _action.send(StartAction.NavigateToSignIn)
            }
        }
    }

    fun navigateToRegistration() {
        viewModelScope.launch {
            _action.send(StartAction.NavigateToSignUp)
        }
    }
}