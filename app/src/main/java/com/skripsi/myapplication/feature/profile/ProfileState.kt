package com.skripsi.myapplication.feature.profile

import com.skripsi.myapplication.model.ProfileData

data class ProfileState(
    val isLoading: Boolean = false,
    val profileData: ProfileData? = null,
    val errorMessage: String? = null
)

