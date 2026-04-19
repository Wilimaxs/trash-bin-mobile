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

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                onScanClick = { viewModel.connect() }
            )
        }
    }
}
