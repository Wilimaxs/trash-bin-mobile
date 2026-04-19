package com.skripsi.myapplication.feature.verify

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.skripsi.myapplication.R
import com.skripsi.myapplication.core.theme.SmartTrashBinTheme
import com.skripsi.myapplication.core.theme.TextSecondary
import com.skripsi.myapplication.core.theme.green
import com.skripsi.myapplication.feature.verify.composable.VerifyHeader
import com.skripsi.myapplication.feature.verify.composable.VerifyOtpInput
import com.skripsi.myapplication.utils.composables.PrimaryButton

@Composable
fun VerifyScreen(
    viewModel: VerifyViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB)),
        containerColor = Color.Transparent,
        topBar = {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .statusBarsPadding()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_left_arrow),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                PrimaryButton(
                    text = "Verify",
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.onVerifyClick(onSuccess = onNavigateToLogin)
                    },
                    enabled = state.isOtpComplete,
                    iconResId = null
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            VerifyHeader()

            Spacer(modifier = Modifier.height(40.dp))

            VerifyOtpInput(
                otpText = state.otpCode,
                onOtpTextChange = viewModel::onOtpChange,
                isError = state.isError,
                isSuccess = state.isSuccess,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (state.timerActive) {
                val formattedTime = "00:${state.timerSeconds.toString().padStart(2, '0')}"
                Text(
                    text = buildAnnotatedString {
                        append("Didn't receive the code? ")
                        withStyle(style = SpanStyle(color = green, fontWeight = FontWeight.Bold)) {
                            append("Resend in $formattedTime")
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary),
                )
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Didn't receive the code? ",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                    )
                    Text(
                        text = "Resend",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = green,
                            fontWeight = FontWeight.Bold,
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                        ),
                        modifier = Modifier.clickable { 
                            focusManager.clearFocus()
                            viewModel.onResendClick() 
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerifyScreenPreview() {
    SmartTrashBinTheme {
        // Dummy screen rendering just to verify UI structure
        // VerifyScreen() 
    }
}
