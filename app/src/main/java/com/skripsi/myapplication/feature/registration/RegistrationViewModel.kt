package com.skripsi.myapplication.feature.registration

import androidx.lifecycle.ViewModel
import com.skripsi.myapplication.utils.validation.ConfirmPasswordValidator
import com.skripsi.myapplication.utils.validation.EmailValidator
import com.skripsi.myapplication.utils.validation.FullNameValidator
import com.skripsi.myapplication.utils.validation.PasswordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state: StateFlow<RegistrationState> = _state.asStateFlow()

    fun onFullNameChange(name: String) {
        _state.update { currentState ->
            currentState.copy(
                fullName = name,
                isFullNameError = false,
                fullNameErrorMessage = null
            )
        }
    }

    fun onEmailChange(email: String) {
        _state.update { currentState ->
            currentState.copy(
                email = email,
                isEmailError = false,
                emailErrorMessage = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _state.update { currentState ->
            currentState.copy(
                password = password,
                isPasswordError = false,
                passwordErrorMessage = null
            )
        }
    }

    fun onConfirmPasswordChange(password: String) {
        _state.update { currentState ->
            currentState.copy(
                confirmPassword = password,
                isConfirmPasswordError = false,
                confirmPasswordErrorMessage = null
            )
        }
    }

    fun onTermsAcceptedChange(accepted: Boolean) {
        _state.update { currentState ->
            currentState.copy(isTermsAccepted = accepted)
        }
    }

    fun onRegisterClick() {
        val currentState = _state.value

        val fullNameError = FullNameValidator.validate(currentState.fullName)
        val emailError = EmailValidator.validate(currentState.email)
        val passwordError = PasswordValidator.validate(currentState.password)
        val confirmPasswordError = ConfirmPasswordValidator.validate(
            password = currentState.password,
            confirmPassword = currentState.confirmPassword
        )

        _state.update {
            it.copy(
                isFullNameError = fullNameError != null,
                fullNameErrorMessage = fullNameError,
                isEmailError = emailError != null,
                emailErrorMessage = emailError,
                isPasswordError = passwordError != null,
                passwordErrorMessage = passwordError,
                isConfirmPasswordError = confirmPasswordError != null,
                confirmPasswordErrorMessage = confirmPasswordError
            )
        }

        if (fullNameError == null && emailError == null && passwordError == null && confirmPasswordError == null) {
            // TODO: Lakukan hit API Register di sini
            // _state.update { it.copy(isLoading = true) }
        }
    }
}
