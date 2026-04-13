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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.skripsi.myapplication.R
import com.skripsi.myapplication.feature.registration.composable.RegistrationForm
import com.skripsi.myapplication.feature.registration.composable.RegistrationHeader
import com.skripsi.myapplication.feature.registration.composable.RegistrationStickyFooter

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB)),
        containerColor = Color.Transparent,
        topBar = {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_left_arrow),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        },
        bottomBar = {
            // Posisi tombol dan checkbox menempel (sticky) di bagian bawah
            RegistrationStickyFooter(
                isTermsAccepted = state.isTermsAccepted,
                onTermsChange = viewModel::onTermsAcceptedChange,
                isFormFilled = state.isFormFilled,
                onRegisterClick = viewModel::onRegisterClick
            )
        }
    ) { paddingValues ->

        // Konten utama bisa discroll ketika form banyak atau di hp kecil
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
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
