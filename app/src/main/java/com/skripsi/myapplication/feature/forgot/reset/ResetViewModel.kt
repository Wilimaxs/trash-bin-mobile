package com.skripsi.myapplication.feature.forgot.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

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

    fun onResetClick(onSuccess: () -> Unit) {
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
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            viewModelScope.launch {
                when (val result = authRepository.resetPassword(
                    newPassword = currentState.newPassword,
                    passwordConfirmation = currentState.confirmPassword
                )) {
                    is NetworkResult.Success -> {
                        _state.update { it.copy(isLoading = false) }
                        onSuccess()
                    }
                    is NetworkResult.Error -> {
                        _state.update { it.copy(isLoading = false, errorMessage = result.message) }
                    }
                    is NetworkResult.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

}