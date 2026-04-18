package com.skripsi.myapplication.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.myapplication.model.ApiResponse
import com.skripsi.myapplication.model.ProfileData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ProfileState(isLoading = true))
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            val mockResponse = ApiResponse(
                status = true,
                message = "Success retrieve user profile",
                data = ProfileData(
                    fullName = "Alex Johnson",
                    email = "wildan27370@gmail.com",
                    avatarUrl = null,
                    memberSince = "Member since 2026",
                    totalPoints = 250,
                    totalItems = 1240
                )
            )

            _state.update {
                it.copy(
                    isLoading = false,
                    profileData = mockResponse.data
                )
            }
        }
    }

    fun onLogoutClick(onLogoutSuccess: () -> Unit) {
        // Tentu ini nanti bisa panggil API Logout atau clear Session Storage
        // Simulasi langsung sukses
        onLogoutSuccess()
    }
}

