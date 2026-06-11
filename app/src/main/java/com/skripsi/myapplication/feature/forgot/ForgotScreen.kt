package com.skripsi.myapplication.feature.forgot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.skripsi.myapplication.R
import com.skripsi.myapplication.core.theme.SmartTrashBinTheme
import com.skripsi.myapplication.feature.forgot.composable.ForgotCheckEmail
import com.skripsi.myapplication.feature.forgot.composable.ForgotDesc
import com.skripsi.myapplication.utils.composables.LoadingOverlay
import com.skripsi.myapplication.utils.snackbar.CustomSnackBarManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToVerify: () -> Unit = {},
    viewModel: ForgotViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { msg ->
            CustomSnackBarManager.showError(msg)
            viewModel.clearError()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 4.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_left_arrow),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            ForgotDesc(
                title = "Forgot your password?",
                desc = "Don't worry, it happens to the best of us. Enter the email associated with your account and we'll help you get back to recycling."
            )
            Spacer(modifier = Modifier.height(32.dp))
            ForgotCheckEmail(
                state = state,
                onEmailChange = viewModel::onEmailChange,
                onSendClick = {
                    viewModel.onSendResetCode(
                        onSuccess = { message ->
                            CustomSnackBarManager.showSuccess(message)
                            onNavigateToVerify()
                        }
                    )
                }
            )
        }
        
        LoadingOverlay(isLoading = state.isLoading)
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    SmartTrashBinTheme {
        ForgotPasswordScreen()
    }
}
