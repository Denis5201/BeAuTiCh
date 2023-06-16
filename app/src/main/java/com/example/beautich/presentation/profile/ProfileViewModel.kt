package com.example.beautich.presentation.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.MessageSource
import com.example.beautich.domain.repository.AvatarRepository
import com.example.beautich.domain.repository.LocalSettings
import com.example.beautich.domain.repository.ProfileRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val avatarRepository: AvatarRepository,
    private val settings: LocalSettings
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    private val _action = Channel<ProfileAction?>()
    val action = _action.receiveAsFlow()

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            profileRepository.getProfile().collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        profile = it
                    )
                    if (!it.avatar.isNullOrEmpty()) {
                        loadPhoto()
                    }
                }.onFailure {
                    _action.send(ProfileAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    fun exit() {
        viewModelScope.launch {
            profileRepository.logout().collect { result ->
                settings.clearUserInfo()
                _action.send(ProfileAction.Logout)
            }
        }
    }

    fun closeErrorDialog() {
        viewModelScope.launch {
            _action.send(null)
        }
    }

    private fun loadPhoto() {
        viewModelScope.launch {
            avatarRepository.getAvatar().collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        avatar = BitmapFactory.decodeByteArray(it, 0, it.size)
                    )
                }.onFailure {
                    _action.send(ProfileAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    private fun uploadNewPhoto(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos)

        viewModelScope.launch {
            avatarRepository.loadNewAvatar(baos.toByteArray()).collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        avatar = bitmap
                    )
                }.onFailure {
                    _action.send(ProfileAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }
}