package com.skripsi.myapplication.utils.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.skripsi.myapplication.R
import com.skripsi.myapplication.core.theme.TextPrimary
import com.skripsi.myapplication.core.theme.TextHint

@Composable
fun TextFormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    hint: String = "",
    isRequired: Boolean = false,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val isFilled = value.isNotEmpty()

    val containerColor = if (isFilled) Color.White else Color(0xFFF3F4F6)

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = buildAnnotatedString {
                append(label)
                if (isRequired) {
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append(" *")
                    }
                }
            },
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = hint, color = TextHint, style = MaterialTheme.typography.bodyMedium)
            },
            isError = isError,
            keyboardOptions = keyboardOptions,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            shape = RoundedCornerShape(size = 12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = Color(0xFFF3F4F6),
                errorContainerColor = containerColor,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color(0xFFE5E7EB),
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                            ),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color.Gray
                        )
                    }
                } else {
                    trailingIcon?.invoke()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}
