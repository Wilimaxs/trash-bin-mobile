package com.skripsi.myapplication.feature.registration

data class RegistrationState(
    val fullName: String = "",
    val isFullNameError: Boolean = false,
    val fullNameErrorMessage: String? = null,

    val email: String = "",
    val isEmailError: Boolean = false,
    val emailErrorMessage: String? = null,

    val password: String = "",
    val isPasswordError: Boolean = false,
    val passwordErrorMessage: String? = null,

    val confirmPassword: String = "",
    val isConfirmPasswordError: Boolean = false,
    val confirmPasswordErrorMessage: String? = null,

    val isTermsAccepted: Boolean = false,
    val isLoading: Boolean = false
) {
    val isFormFilled: Boolean
        get() = fullName.isNotEmpty() && 
                email.isNotEmpty() && 
                password.isNotEmpty() && 
                confirmPassword.isNotEmpty() && 
                isTermsAccepted
}