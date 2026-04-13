package com.skripsi.myapplication.feature.login.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.skripsi.myapplication.core.theme.SmartTrashBinTheme
import com.skripsi.myapplication.core.theme.TextPrimary
import com.skripsi.myapplication.core.theme.TextSecondary

@Composable
fun LoginTitle() {
    Column {
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sign in to continue tracking your recycling rewards and carbon footprint.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = TextSecondary
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginTitlePreview() {
    SmartTrashBinTheme {
        LoginTitle()
    }
}