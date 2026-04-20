package com.skripsi.myapplication.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.skripsi.myapplication.feature.home.components.ConnectedContent
import com.skripsi.myapplication.feature.home.components.ScanOverlay

import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { errorMsg ->
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isConnected) {
            ConnectedContent(
                state = uiState,
                onStopClick = { viewModel.disconnect() }
            )
        } else {
            ConnectedContent(
                state = uiState,
                isScrollEnabled = false,
                onStopClick = { }
            )
            ScanOverlay(
                onScanClick = { viewModel.startScan() }
            )
        }
    }
}
