package com.example.beautich.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.MessageSource
import com.example.beautich.domain.Validator
import com.example.beautich.domain.model.Credentials
import com.example.beautich.domain.repository.AuthRepository
import com.example.beautich.domain.repository.ProfileRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepository,
    profileRepository: ProfileRepository,
    private val validator: Validator,
    private val messageSource: MessageSource
) : ViewModel() {

    private val _action = Channel<SignInAction?>()
    val action = _action.receiveAsFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    fun setEmail(value: String) {
        _email.value = value
    }

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    fun setPassword(value: String) {
        _password.value = value
    }

    init {
        viewModelScope.launch {
            if (profileRepository.isSubscribing().single().isSuccess) {
                _action.send(SignInAction.NavigateToMain)
            }
        }
    }

    fun signIn() {
        if (validator.isStringsEmpty(_email.value.trim(), _password.value.trim())
        ) {
            showError(MessageSource.EMPTY_INPUT)
            return
        }
        if (!validator.isEmailFormat(_email.value.trim())) {
            showError(MessageSource.WRONG_EMAIL_FORMAT)
            return
        }
        viewModelScope.launch {
            authRepository.signIn(
                Credentials(_email.value.trim(), _password.value.trim())
            ).collect { result ->
                result.onSuccess {
                    _action.send(SignInAction.NavigateToMain)
                }.onFailure {
                    _action.send(SignInAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    fun closeErrorDialog() {
        viewModelScope.launch {
            _action.send(null)
        }
    }

    fun navigateToSignUp() {
        viewModelScope.launch {
            _action.send(SignInAction.NavigateToSignUp)
        }
    }

    private fun showError(reason: Int) {
        viewModelScope.launch {
            _action.send(SignInAction.ShowError(messageSource.getMessage(reason)))
        }
    }
}