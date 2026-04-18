package com.skripsi.myapplication.feature.verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class VerifyViewModel @Inject constructor() : ViewModel() {

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

    fun onVerifyClick() {
        val currentOtp = _state.value.otpCode

        if (currentOtp.length == 6) {
            // TODO: Ganti logika ini dengan pemanggilan API Verify sebetulnya
            // Contoh mock test: Jika "123456" sukses, sisanya dianggap salah
            if (currentOtp == "123456") {
                _state.update { it.copy(isSuccess = true, isError = false) }
            } else {
                _state.update { it.copy(isError = true, isSuccess = false) }
            }
        }
    }

    fun onResendClick() {
        startTimer()
        _state.update { it.copy(otpCode = "", isError = false, isSuccess = false) }
    }
}
