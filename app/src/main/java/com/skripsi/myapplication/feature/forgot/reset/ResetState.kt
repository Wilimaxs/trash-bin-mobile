package com.skripsi.myapplication.feature.forgot.reset

data class ResetState(
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isNewPasswordError: Boolean = false,
    val newPasswordErrorMessage: String? = null,
    val isConfirmPasswordError: Boolean = false,
    val confirmPasswordErrorMessage: String? = null,
    val isFormValid: Boolean = false
)