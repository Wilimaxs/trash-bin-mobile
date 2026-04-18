package com.skripsi.myapplication.utils.snackbar

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

enum class SnackBarType {
    SUCCESS, ERROR, INFO
}

data class SnackBarData(
    val id: String = UUID.randomUUID().toString(),
    val message: String,
    val type: SnackBarType
)

object CustomSnackBarManager {
    private val _snackBarState = MutableStateFlow<SnackBarData?>(value = null)
    val snackBarState: StateFlow<SnackBarData?> = _snackBarState.asStateFlow()

    fun showSuccess(message: String) {
        _snackBarState.update { SnackBarData(message = message, type = SnackBarType.SUCCESS) }
    }

    fun showError(message: String) {
        _snackBarState.update { SnackBarData(message = message, type = SnackBarType.ERROR) }
    }

    fun showInfo(message: String) {
        _snackBarState.update { SnackBarData(message = message, type = SnackBarType.INFO) }
    }

    fun dismiss() {
        _snackBarState.update { null }
    }
}

