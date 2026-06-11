package com.skripsi.myapplication.feature.forgot

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.model.ForgotPasswordRequest
import com.skripsi.myapplication.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ForgotState())
    val state: StateFlow<ForgotState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val emailErrorMessage = if (!isEmailValid && email.isNotEmpty()) {
            "Invalid email format"
        } else {
            null
        }

        _state.update {
            it.copy(
                email = email,
                isEmailError = !isEmailValid && email.isNotEmpty(),
                emailErrorMessage = emailErrorMessage,
                errorMessage = null // Clear previous errors on input change
            )
        }
    }

    fun onSendResetCode(onSuccess: (String) -> Unit) {
        if (!_state.value.isFormValid) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            
            val request = ForgotPasswordRequest(email = _state.value.email)
            when (val result = authRepository.forgotPassword(request)) {
                is NetworkResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    onSuccess("If your email is registered, you will receive an OTP code to reset your password.")
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

    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}
