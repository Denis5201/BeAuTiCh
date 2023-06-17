package com.example.beautich.presentation.profile

import android.graphics.Bitmap
import android.net.Uri
import com.example.beautich.domain.model.Profile

data class ProfileUiState(
    val isLoading: Boolean = true,
    val isPhotoDialogOpen: Boolean = false,
    val isChangeProfileDialogOpen: Boolean = false,
    val uri: Uri = Uri.EMPTY,
    val profile: Profile? = null,
    val avatar: Bitmap? = null
)
