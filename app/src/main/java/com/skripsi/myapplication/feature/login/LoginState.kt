package com.skripsi.myapplication.feature.login

data class LoginState(
    val email: String = "",
    val isEmailError: Boolean = false,
    val emailErrorMessage: String? = null,

    val password: String = "",
    val isPasswordError: Boolean = false,
    val passwordErrorMessage: String? = null,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormFilled: Boolean
        get() = email.isNotEmpty() && password.isNotEmpty()
}
