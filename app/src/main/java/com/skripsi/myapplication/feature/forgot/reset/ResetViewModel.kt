package com.skripsi.myapplication.feature.forgot.reset

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ResetViewModel : ViewModel() {

    private val _state = MutableStateFlow(ResetState())
    val state: StateFlow<ResetState> = _state.asStateFlow()

    private fun isFormValid(newPassword: String, confirmPassword: String): Boolean {
        return newPassword.length >= 8 && confirmPassword.isNotEmpty()
    }

    fun onNewPasswordChange(password: String) {
        _state.update { currentState ->
            currentState.copy(
                newPassword = password,
                isNewPasswordError = false,
                newPasswordErrorMessage = null,
                isFormValid = isFormValid(password, currentState.confirmPassword)
            )
        }
    }

    fun onConfirmPasswordChange(password: String) {
        _state.update { currentState ->
            currentState.copy(
                confirmPassword = password,
                isConfirmPasswordError = false,
                confirmPasswordErrorMessage = null,
                isFormValid = isFormValid(currentState.newPassword, password)
            )
        }
    }

    fun onResetClick() {
        val currentState = _state.value

        val newPasswordError = if (currentState.newPassword.length < 8)
            "Password must be at least 8 characters" else null
        val confirmPasswordError = if (currentState.newPassword != currentState.confirmPassword)
            "Passwords do not match" else null

        _state.update {
            it.copy(
                isNewPasswordError = newPasswordError != null,
                newPasswordErrorMessage = newPasswordError,
                isConfirmPasswordError = confirmPasswordError != null,
                confirmPasswordErrorMessage = confirmPasswordError
            )
        }

        if (newPasswordError == null && confirmPasswordError == null) {
            // TODO: trigger reset password logic
        }
    }

}