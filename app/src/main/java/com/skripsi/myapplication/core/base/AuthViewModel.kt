package com.skripsi.myapplication.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.myapplication.core.local.LocalStorage
import com.skripsi.myapplication.core.local.SecureStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val secureStorage: SecureStorage,
    private val localStorage: LocalStorage
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkAuthState()
        observeSessionEvent()
    }

    private fun observeSessionEvent() {
        viewModelScope.launch {
            secureStorage.sessionExpiredEvent.collectLatest {
                logout() // Panggil logout otomatis ketika menerima event expired
            }
        }
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            delay(1000)

            val hasSeenOnboarding = localStorage.hasSeenOnboarding.first()

            if (!hasSeenOnboarding) {
                _authState.value = AuthState.Onboarding
            } else {
                val token = secureStorage.getToken()
                val refreshToken = secureStorage.getRefreshToken()

                if (token.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
                    _authState.value = AuthState.Unauthenticated
                } else {
                    _authState.value = AuthState.Authenticated
                }
            }
        }
    }

    // Dipanggil saat login berhasil
    fun setAuthenticated(token: String, refreshToken: String, userJson: String? = null) {
        secureStorage.saveToken(token)
        secureStorage.saveRefreshToken(refreshToken)
        if (userJson != null) {
            viewModelScope.launch {
                localStorage.saveUserData(userJson)
            }
        }
        _authState.value = AuthState.Authenticated
    }

    // Dipanggil saat logout
    fun logout() {
        secureStorage.clearAuth()
        viewModelScope.launch {
            localStorage.clearAll()
            _authState.value = AuthState.Unauthenticated
        }
    }

    // Dipanggil saat Onboarding selesai
    fun finishOnboarding() {
        viewModelScope.launch {
            localStorage.saveOnboardingState(true)
            _authState.value = AuthState.Unauthenticated
        }
    }
}
