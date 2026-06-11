package com.skripsi.myapplication.feature.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.skripsi.myapplication.R
import com.skripsi.myapplication.core.theme.SmartTrashBinTheme
import com.skripsi.myapplication.feature.login.composable.LoginFooter
import com.skripsi.myapplication.feature.login.composable.LoginForm
import com.skripsi.myapplication.feature.login.composable.LoginIconApp
import com.skripsi.myapplication.feature.login.composable.LoginTitle
import com.skripsi.myapplication.utils.composables.LoadingOverlay
import com.skripsi.myapplication.utils.composables.PrimaryButton
import com.skripsi.myapplication.utils.snackbar.CustomSnackBarManager

@Composable
fun LoginScreen(
    onNavigateToSignUp: () -> Unit = {},
    onNavigateToMainRoute: (String, String) -> Unit = { _, _ -> },
    onNavigateToForgot: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { msg ->
            CustomSnackBarManager.showError(msg)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(all = 12.dp)
        ) {
            LoginIconApp()
            Spacer(modifier = Modifier.height(24.dp))
            LoginTitle()
            Spacer(modifier = Modifier.height(32.dp))
            LoginForm(
                emailChange = viewModel::onEmailChange,
                state = state,
                passwordChange = viewModel::onPasswordChange,
                onNavigateToForgot = onNavigateToForgot
            )
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(
                text = "Sign In",
                onClick = {
                    focusManager.clearFocus()
                    viewModel.onLoginClickWithCallback(onSuccess = onNavigateToMainRoute)
                },
                enabled = state.isFormFilled,
                iconResId = R.drawable.ic_right_arrow
            )
            Spacer(modifier = Modifier.weight(1f))
            LoginFooter(onNavigateToSignUp = onNavigateToSignUp)
        }

        LoadingOverlay(isLoading = state.isLoading)

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    SmartTrashBinTheme {
        LoginScreen()
    }
}
