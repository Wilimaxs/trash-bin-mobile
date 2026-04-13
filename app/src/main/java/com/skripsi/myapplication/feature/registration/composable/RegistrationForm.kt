package com.skripsi.myapplication.feature.registration.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.skripsi.myapplication.core.theme.SmartTrashBinTheme
import com.skripsi.myapplication.feature.registration.RegistrationState
import com.skripsi.myapplication.utils.composables.TextFormField

@Composable
fun RegistrationForm(
    state: RegistrationState,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.fillMaxWidth()) {

        TextFormField(
            value = state.fullName,
            onValueChange = onFullNameChange,
            label = "Full Name",
            hint = "Enter Your Full Name",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            isError = state.isFullNameError,
            errorMessage = state.fullNameErrorMessage
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextFormField(
            value = state.email,
            onValueChange = onEmailChange,
            label = "Email Address",
            hint = "you@example.com",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            isError = state.isEmailError,
            errorMessage = state.emailErrorMessage
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextFormField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = "Password",
            hint = "........",
            isPassword = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            isError = state.isPasswordError,
            errorMessage = state.passwordErrorMessage
        )

        Spacer(modifier = Modifier.height(6.dp))

        Spacer(modifier = Modifier.height(16.dp))

        TextFormField(
            value = state.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = "Confirm Password",
            hint = "........",
            isPassword = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus(force = true)
                }
            ),
            isError = state.isConfirmPasswordError,
            errorMessage = state.confirmPasswordErrorMessage
        )

        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationFormPreview() {
    SmartTrashBinTheme {
        RegistrationForm(
            state = RegistrationState(),
            onFullNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {}
        )
    }
}
