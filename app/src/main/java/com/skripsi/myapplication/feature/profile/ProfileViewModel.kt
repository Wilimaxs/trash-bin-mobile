package com.skripsi.myapplication.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.repository.AuthRepository
import com.skripsi.myapplication.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState(isLoading = true))
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = userRepository.getProfile()) {
                is NetworkResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            profileData = result.data
                        )
                    }
                }
                is NetworkResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
                is NetworkResult.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun refreshProfileData() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true, errorMessage = null) }

            when (val result = userRepository.getProfile()) {
                is NetworkResult.Success -> {
                    _state.update {
                        it.copy(
                            isRefreshing = false,
                            profileData = result.data
                        )
                    }
                }
                is NetworkResult.Error -> {
                    _state.update {
                        it.copy(
                            isRefreshing = false,
                            errorMessage = result.message
                        )
                    }
                }
                is NetworkResult.Loading -> {
                    // Do not overwrite isRefreshing here, let it be handled by refresh flow
                }
            }
        }
    }

    fun onLogoutClick(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            // 1. Hit API Logout
            authRepository.logout()
            // 2. Trigger success callback which will clear local config and navigate
            _state.update { it.copy(isLoading = false) }
            onLogoutSuccess()
        }
    }
}
