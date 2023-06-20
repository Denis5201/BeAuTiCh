package com.example.beautich.presentation.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautich.MessageSource
import com.example.beautich.domain.model.ChangeProfile
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

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName
    fun setFullName(value: String) {
        _fullName.value = value
    }

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone
    fun setPhone(value: String) {
        _phone.value = value
    }

    private val _oldPassword = MutableStateFlow("")
    val oldPassword: StateFlow<String> = _oldPassword
    fun setOldPassword(value: String) {
        _oldPassword.value = value
    }

    private val _newPassword = MutableStateFlow("")
    val newPassword: StateFlow<String> = _newPassword
    fun setNewPassword(value: String) {
        _newPassword.value = value
    }

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            profileRepository.getProfile().collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        profile = it
                    )
                    if (it.avatar) {
                        loadPhoto()
                    }
                    _fullName.value = it.fullName
                    _phone.value = it.phoneNumber ?: ""
                }.onFailure {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                    _action.send(ProfileAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    fun exit() {
        viewModelScope.launch {
            profileRepository.logout().collect {
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

    fun openPhotoDialog() {
        _uiState.value = _uiState.value.copy(
            isPhotoDialogOpen = true
        )
    }

    fun closePhotoDialog() {
        _uiState.value = _uiState.value.copy(
            isPhotoDialogOpen = false
        )
    }

    fun openChangeProfileDialog() {
        _uiState.value = _uiState.value.copy(
            isChangeProfileDialogOpen = true
        )
    }

    fun closeChangeProfileDialog() {
        _uiState.value = _uiState.value.copy(
            isChangeProfileDialogOpen = false
        )
    }

    fun openChangePasswordDialog() {
        _uiState.value = _uiState.value.copy(
            isChangePasswordDialogOpen = true
        )
    }

    fun closeChangePasswordDialog() {
        _uiState.value = _uiState.value.copy(
            isChangePasswordDialogOpen = false
        )
    }

    fun setUri(newUri: Uri) {
        _uiState.value = _uiState.value.copy(
            uri = newUri
        )
    }

    fun deletePhoto() {
        viewModelScope.launch {
            avatarRepository.deleteAvatar().collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        avatar = null,
                        profile = _uiState.value.profile?.copy(
                            avatar = false
                        )
                    )
                }.onFailure {
                    _action.send(ProfileAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
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

    fun uploadNewPhoto(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos)

        _uiState.value = _uiState.value.copy(
            isPhotoDialogOpen = false
        )

        viewModelScope.launch {
            avatarRepository.loadNewAvatar(baos.toByteArray()).collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        avatar = bitmap,
                        profile = _uiState.value.profile?.copy(avatar = true)
                    )
                }.onFailure {
                    _action.send(ProfileAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    fun changeProfile() {
        if (_fullName.value.trim().isEmpty()) {
            return
        }
        _uiState.value = _uiState.value.copy(
            isChangeProfileDialogOpen = false
        )
        viewModelScope.launch {
            profileRepository.changeProfile(
                ChangeProfile(_fullName.value.trim(), _phone.value.trim())
            ).collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        profile = _uiState.value.profile!!.copy(
                            fullName = _fullName.value,
                            phoneNumber = _phone.value
                        )
                    )
                }.onFailure {
                    _action.send(ProfileAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    fun changePassword() {
        if (_oldPassword.value.trim().isEmpty() || _newPassword.value.trim().isEmpty()) {
            return
        }
        _uiState.value = _uiState.value.copy(
            isChangePasswordDialogOpen = false
        )
        viewModelScope.launch {
            profileRepository.changePassword(
                _oldPassword.value.trim(), _newPassword.value.trim()
            ).collect { result ->
                result.onSuccess {
                    _oldPassword.value = ""
                    _newPassword.value = ""
                }.onFailure {
                    _action.send(ProfileAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }
}