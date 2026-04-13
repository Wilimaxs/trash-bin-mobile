package com.skripsi.myapplication.feature.login.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.skripsi.myapplication.core.theme.SmartTrashBinTheme
import com.skripsi.myapplication.core.theme.green
import com.skripsi.myapplication.feature.login.LoginState
import com.skripsi.myapplication.utils.composables.TextFormField

@Composable
fun LoginForm(
    emailChange: (String) -> Unit,
    state: LoginState,
    passwordChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column {
        TextFormField(
            value = state.email,
            onValueChange = emailChange,
            label = "Email Address",
            hint = "hello@example.com",
            isRequired = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = state.isEmailError,
            errorMessage = state.emailErrorMessage,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Form Password
        TextFormField(
            value = state.password,
            onValueChange = passwordChange,
            label = "Password",
            hint = "........",
            isRequired = true,
            isPassword = true,
            isError = state.isPasswordError,
            errorMessage = state.passwordErrorMessage,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Forgot Password?",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = green,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .clickable { /* Handle Forgot Password */ }
                .padding(vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginFormPreview() {
    SmartTrashBinTheme {
        LoginForm(
            emailChange = {},
            state = LoginState(),
            passwordChange = {}
        )
    }
}