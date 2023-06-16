package com.example.beautich.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.MessageSource
import com.example.beautich.domain.Validator
import com.example.beautich.domain.model.RegistrationForm
import com.example.beautich.domain.repository.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val validator: Validator,
    private val messageSource: MessageSource
) : ViewModel() {

    private val _action = Channel<SignUpAction?>()
    val action = _action.receiveAsFlow()

    private val _surname = MutableStateFlow("")
    val surname: StateFlow<String> = _surname
    fun setSurname(value: String) {
        _surname.value = value
    }

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name
    fun setName(value: String) {
        _name.value = value
    }

    private val _patronymic = MutableStateFlow("")
    val patronymic: StateFlow<String> = _patronymic
    fun setPatronymic(value: String) {
        _patronymic.value = value
    }

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

    private val _confirm = MutableStateFlow("")
    val confirm: StateFlow<String> = _confirm
    fun setConfirm(value: String) {
        _confirm.value = value
    }

    fun signUp() {
        if (validator.isStringsEmpty(
                _surname.value.trim(), _name.value.trim(),
                _email.value.trim(), _password.value.trim(), _confirm.value.trim())
        ) {
            showError(MessageSource.EMPTY_INPUT)
            return
        }
        if (!validator.isEmailFormat(_email.value.trim())) {
            showError(MessageSource.WRONG_EMAIL_FORMAT)
            return
        }
        if (!validator.isPasswordForm(_password.value.trim())) {
            showError(MessageSource.WRONG_FORM_PASSWORD)
            return
        }
        if (_password.value.trim() != _confirm.value) {
            showError(MessageSource.PASSWORD_NOT_EQUAL_WITH_CONFIRM)
            return
        }
        viewModelScope.launch {
            val endFullName = " ${_patronymic.value.trim().ifEmpty { "" }}"
            authRepository.signUp(
                RegistrationForm(_email.value.trim(),
                    "${_surname.value.trim()} ${_name.value.trim()}$endFullName",
                    _password.value.trim(), LocalDate.now().minusDays(2), null)
            ).collect { result ->
                result.onSuccess {
                    _action.send(SignUpAction.NavigateToMain)
                }.onFailure {
                    _action.send(SignUpAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    fun closeErrorDialog() {
        viewModelScope.launch {
            _action.send(null)
        }
    }

    fun navigateToSignIn() {
        viewModelScope.launch {
            _action.send(SignUpAction.NavigateToSignIn)
        }
    }

    private fun showError(reason: Int) {
        viewModelScope.launch {
            _action.send(SignUpAction.ShowError(messageSource.getMessage(reason)))
        }
    }
}