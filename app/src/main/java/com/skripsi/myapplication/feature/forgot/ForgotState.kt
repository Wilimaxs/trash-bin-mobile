package com.skripsi.myapplication.feature.forgot

data class ForgotState(
    val email: String = "",
    val isLoading: Boolean = false,
    val isEmailError: Boolean = false,
    val emailErrorMessage: String? = null,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = email.isNotBlank() && !isEmailError
}
