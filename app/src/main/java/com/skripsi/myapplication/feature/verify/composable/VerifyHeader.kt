package com.skripsi.myapplication.feature.verify.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skripsi.myapplication.core.theme.TextPrimary
import com.skripsi.myapplication.core.theme.TextSecondary
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Surface

@Composable
fun VerifyHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Verify OTP",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Please enter the 6-digit verification code\nsent to your email address.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = TextSecondary,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VerifyHeaderPreview() {
    Surface {
        VerifyHeader()
    }
}
