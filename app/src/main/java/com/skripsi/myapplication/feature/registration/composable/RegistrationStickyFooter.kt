package com.skripsi.myapplication.feature.registration.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.skripsi.myapplication.core.theme.SmartTrashBinTheme
import com.skripsi.myapplication.core.theme.TextSecondary
import com.skripsi.myapplication.core.theme.green
import com.skripsi.myapplication.utils.composables.PrimaryButton

@Composable
fun RegistrationStickyFooter(
    isTermsAccepted: Boolean,
    onTermsChange: (Boolean) -> Unit,
    isFormFilled: Boolean,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Checkbox(
                checked = isTermsAccepted,
                onCheckedChange = onTermsChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = green,
                    uncheckedColor = Color.LightGray
                )
            )

            Text(
                text = buildAnnotatedString {
                    append("I agree to the ")
                    withStyle(style = SpanStyle(color = green)) {
                        append("Terms & Conditions")
                    }
                },
                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary),
            )
        }

        PrimaryButton(
            text = "Register",
            onClick = onRegisterClick,
            enabled = isFormFilled,
            iconResId = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationStickyFooterPreview() {
    SmartTrashBinTheme {
        RegistrationStickyFooter(
            isTermsAccepted = true,
            onTermsChange = {},
            isFormFilled = true,
            onRegisterClick = {}
        )
    }
}

