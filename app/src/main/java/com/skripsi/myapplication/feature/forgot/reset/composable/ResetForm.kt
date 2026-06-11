package com.skripsi.myapplication.feature.forgot.reset.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.skripsi.myapplication.feature.forgot.reset.ResetState
import com.skripsi.myapplication.utils.composables.PrimaryButton
import com.skripsi.myapplication.utils.composables.TextFormField

@Composable
fun ResetPasswordForm(
    state: ResetState,
    onNewPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onResetClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxWidth()) {
        TextFormField(
            value = state.newPassword,
            onValueChange = onNewPasswordChange,
            label = "New Password",
            hint = "Enter new password",
            isPassword = true,
            isError = state.isNewPasswordError,
            errorMessage = state.newPasswordErrorMessage,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(24.dp))

        TextFormField(
            value = state.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = "Confirm New Password",
            hint = "Re-enter new password",
            isPassword = true,
            isError = state.isConfirmPasswordError,
            errorMessage = state.confirmPasswordErrorMessage,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = "Reset Password",
            onClick = {
                focusManager.clearFocus()
                onResetClick()
            },
            enabled = state.isFormValid
        )
    }
}