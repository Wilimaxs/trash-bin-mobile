package com.skripsi.myapplication.feature.registration.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.skripsi.myapplication.core.theme.SmartTrashBinTheme
import com.skripsi.myapplication.feature.login.composable.LoginIconApp

@Composable
fun RegistrationHeader() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LoginIconApp()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Create EcoBin Account",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Join the green revolution and get rewarded for every item you recycle.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationHeaderPreview() {
    SmartTrashBinTheme {
        RegistrationHeader()
    }
}
