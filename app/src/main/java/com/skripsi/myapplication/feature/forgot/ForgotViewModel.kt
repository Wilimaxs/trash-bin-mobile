package com.skripsi.myapplication.feature.forgot

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    // private val authRepository: AuthRepository // if needed later
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

    fun onSendResetCode(onSuccess: () -> Unit) {
        if (!_state.value.isFormValid) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            
            // TODO: Replace with actual API call
            delay(1000) // Mock network delay
            
            _state.update { it.copy(isLoading = false) }
            onSuccess()
        }
    }

    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}
