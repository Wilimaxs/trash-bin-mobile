package com.skripsi.myapplication.feature.verify.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skripsi.myapplication.core.theme.TextPrimary
import com.skripsi.myapplication.core.theme.green
import androidx.compose.material3.Surface

@Composable
fun VerifyOtpInput(
    otpText: String,
    onOtpTextChange: (String) -> Unit,
    isError: Boolean,
    isSuccess: Boolean,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        value = otpText,
        onValueChange = onOtpTextChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        modifier = modifier.focusRequester(focusRequester),
        decorationBox = {
            // Container 6 box
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(6) { index ->
                    val char = when {
                        index >= otpText.length -> ""
                        else -> otpText[index].toString()
                    }

                    val isFocused = index == otpText.length
                    val borderColor = when {
                        isError -> MaterialTheme.colorScheme.error
                        isSuccess -> green
                        isFocused -> Color.Black
                        else -> Color.LightGray
                    }

                    val borderWidth = if (isFocused || isError || isSuccess) 2.dp else 1.dp

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(0.85f)
                            .padding(horizontal = 4.dp)
                            .border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(12.dp))
                            .background(Color.White, shape = RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun VerifyOtpInputPreview() {
    Surface(modifier = Modifier.padding(16.dp)) {
        VerifyOtpInput(
            otpText = "123",
            onOtpTextChange = {},
            isError = false,
            isSuccess = false
        )
    }
}
