package com.skripsi.myapplication.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.skripsi.myapplication.feature.home.components.ConnectedContent
import com.skripsi.myapplication.feature.home.components.ScanOverlay

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Base content (always showing connected content behind the overlay to match visual, or conditional)
        // Per user request, it's connected vs disconnected.
        if (uiState.isConnected) {
            ConnectedContent(
                state = uiState,
                onStopClick = { viewModel.disconnect() }
            )
        } else {
            // Background is technically blurred or dark version of the dashboard.
            // We'll show the ConnectedContent behind the overlay, but disable interactions.
            ConnectedContent(
                state = uiState,
                onStopClick = { }
            )
            ScanOverlay(
                onScanClick = { viewModel.connect() }
            )
        }
    }
}
