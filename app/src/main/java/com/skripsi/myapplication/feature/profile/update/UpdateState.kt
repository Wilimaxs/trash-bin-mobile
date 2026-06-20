package com.skripsi.myapplication.feature.profile.update

import android.net.Uri

data class EditProfileState(
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val avatarUri: Uri? = null,
    val currentAvatarUrl: String? = null,

    val fullNameError: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
