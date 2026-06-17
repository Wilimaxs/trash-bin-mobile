package com.skripsi.myapplication.feature.profile.update

import android.net.Uri

data class EditProfileState(
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val avatarUri: Uri? = null,
    val isLoading: Boolean = false,
    val fullNameError: String? = null
)
