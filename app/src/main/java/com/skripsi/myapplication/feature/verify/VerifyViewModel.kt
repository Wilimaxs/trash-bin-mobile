package com.skripsi.myapplication.feature.verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.repository.AuthRepository
import com.skripsi.myapplication.utils.snackbar.CustomSnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(VerifyState())
    val state: StateFlow<VerifyState> = _state.asStateFlow()

    private var timerJob: Job? = null

    init {
        startTimer()
    }

    fun onOtpChange(newValue: String) {
        // Hanya memproses input digit dan maksimal 6 karakter
        if (newValue.length <= 6 && newValue.all { it.isDigit() }) {
            _state.update {
                it.copy(
                    otpCode = newValue,
                    isError = false,   // Hilangkan error kalau user mengetik membetulkan
                    isSuccess = false
                )
            }
        }
    }

    fun startTimer() {
        timerJob?.cancel()
        _state.update { it.copy(timerActive = true, timerSeconds = 60) }

        timerJob = viewModelScope.launch {
            while (_state.value.timerSeconds > 0) {
                delay(1000)
                _state.update { it.copy(timerSeconds = it.timerSeconds - 1) }
            }
            // Kalau waktu habis
            _state.update { it.copy(timerActive = false) }
        }
    }

    fun onVerifyClick(onSuccess: () -> Unit) {
        val currentOtp = _state.value.otpCode

        if (currentOtp.length == 6) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            
            viewModelScope.launch {
                when (val result = authRepository.verifyOtp(currentOtp)) {
                    is NetworkResult.Success -> {
                        _state.update { it.copy(isLoading = false, isSuccess = true, isError = false) }
                        CustomSnackBarManager.showSuccess("Account successfully verified. You can now login.")
                        onSuccess()
                    }
                    is NetworkResult.Error -> {
                        _state.update { it.copy(isLoading = false, isError = true, isSuccess = false, errorMessage = result.message) }
                    }
                    is NetworkResult.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun onVerifyForgotClick(onSuccess: () -> Unit) {
        val currentOtp = _state.value.otpCode

        if (currentOtp.length == 6) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            viewModelScope.launch {
                when (val result = authRepository.verifyOtpForgot(currentOtp)) {
                    is NetworkResult.Success -> {
                        _state.update { it.copy(isLoading = false, isSuccess = true, isError = false) }
                        CustomSnackBarManager.showSuccess("OTP verified. Please set your new password.")
                        onSuccess()
                    }
                    is NetworkResult.Error -> {
                        _state.update { it.copy(isLoading = false, isError = true, isSuccess = false, errorMessage = result.message) }
                    }
                    is NetworkResult.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun onResendClick() {
        startTimer()
        _state.update { it.copy(otpCode = "", isError = false, isSuccess = false) }
        CustomSnackBarManager.showInfo("OTP has been resent")
    }
}
