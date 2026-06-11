package com.skripsi.myapplication.feature.forgot.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skripsi.myapplication.feature.forgot.ForgotState
import com.skripsi.myapplication.utils.composables.PrimaryButton
import com.skripsi.myapplication.utils.composables.TextFormField
import com.skripsi.myapplication.R

@Composable
fun ForgotCheckEmail(
    state: ForgotState,
    onEmailChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextFormField(
            value = state.email,
            onValueChange = onEmailChange,
            label = "Email Address",
            hint = "user@example.com",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            isError = state.isEmailError,
            errorMessage = state.emailErrorMessage
        )
        Spacer(modifier = Modifier.height(32.dp))
        PrimaryButton(
            text = "Send Reset Code",
            onClick = {
                focusManager.clearFocus()
                onSendClick()
            },
            enabled = state.isFormValid,
            iconResId = R.drawable.ic_right_arrow // using ic_right_arrow as seen in login
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotCheckEmailPreview() {
    ForgotCheckEmail(
        state = ForgotState(email = "test@example.com"),
        onEmailChange = {},
        onSendClick = {}
    )
}