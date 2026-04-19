package com.skripsi.myapplication.feature.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.skripsi.myapplication.R
import com.skripsi.myapplication.core.theme.SmartTrashBinTheme
import com.skripsi.myapplication.feature.registration.composable.RegistrationForm
import com.skripsi.myapplication.feature.registration.composable.RegistrationHeader
import com.skripsi.myapplication.feature.registration.composable.RegistrationStickyFooter

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToVerify: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

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
        },
        bottomBar = {
            Box(
                modifier = Modifier.navigationBarsPadding()
            ) {
                RegistrationStickyFooter(
                    isTermsAccepted = state.isTermsAccepted,
                    onTermsChange = viewModel::onTermsAcceptedChange,
                    isFormFilled = state.isFormFilled,
                    onRegisterClick = {
                        focusManager.clearFocus()
                        viewModel.onRegisterClick(
                            onSuccess = { onNavigateToVerify() }
                        )
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp)
        ) {

            RegistrationHeader()

            Spacer(modifier = Modifier.height(32.dp))

            RegistrationForm(
                state = state,
                onFullNameChange = viewModel::onFullNameChange,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onConfirmPasswordChange = viewModel::onConfirmPasswordChange
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    SmartTrashBinTheme {
        RegistrationScreen()
    }
}
