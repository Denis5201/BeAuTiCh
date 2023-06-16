package com.example.beautich.presentation.profile

import android.graphics.Bitmap
import com.example.beautich.domain.model.Profile

data class ProfileUiState(
    val isLoading: Boolean = true,
    val isDialogOpen: Boolean = false,
    val profile: Profile? = null,
    val avatar: Bitmap? = null
)
