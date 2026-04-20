package com.skripsi.myapplication.feature.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val application: Application,
    private val repository: HomeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    private var streamJob: Job? = null

    init {
        startStreaming()
    }

    private fun startStreaming() {
        streamJob?.cancel()
        streamJob = viewModelScope.launch {
            repository.streamDashboard()
                .catch { e ->
                    // Handle error, maybe retry or show error message
                    _uiState.update { it.copy(errorMessage = e.message) }
                }
                .collect { update ->
                    _uiState.update { state ->
                        state.copy(
                            isConnected = update.isConnected,
                            rvmName = update.binName ?: state.rvmName,
                            organicPercent = update.capacityOrganic ?: state.organicPercent,
                            anorganicPercent = update.capacityInorganic ?: state.anorganicPercent,
                            b3Percent = update.capacityB3 ?: state.b3Percent,
                            totalItems = update.totalItems ?: state.totalItems,
                            totalPoints = update.totalPoints ?: state.totalPoints,
                            liveActivities = update.liveActivity ?: state.liveActivities,
                            errorMessage = update.message
                        )
                    }
                }
        }
    }

    fun startScan() {
        val scanner = GmsBarcodeScanning.getClient(application)
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                val rawValue = barcode.rawValue
                if (!rawValue.isNullOrBlank()) {
                    connectSession(rawValue)
                }
            }
            .addOnFailureListener { e ->
                _uiState.update { it.copy(errorMessage = e.message) }
            }
    }

    private fun connectSession(qrCode: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = repository.connectSession(qrCode)) {
                is NetworkResult.Success -> {
                    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isConnected = true,
                            qrCode = qrCode,
                            connectedOn = sdf.format(Date()),
                            errorMessage = null
                        )
                    }
                    startStreaming()
                }
                is NetworkResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
                is NetworkResult.Loading -> Unit // should not be reached via suspend call returning state directly
            }
        }
    }

    fun disconnect() {
        val currentQr = _uiState.value.qrCode
        if (currentQr == null) {
            _uiState.update { it.copy(isConnected = false) } // Just a fallback
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = repository.disconnectSession(currentQr)) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isConnected = false,
                            qrCode = null,
                            errorMessage = null
                        )
                    }
                    streamJob?.cancel()
                }
                is NetworkResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
