package com.skripsi.myapplication.feature.onboarding


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skripsi.myapplication.R
import com.skripsi.myapplication.core.theme.TextPrimary
import com.skripsi.myapplication.core.theme.green

@Composable
fun OnBoardingScreen(
//    onGetStartedClick: () -> Unit,
//    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OnboardingHeader()

        Spacer(modifier = Modifier.weight(1f))

        OnboardingContent()

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {},
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = green,
                contentColor = TextPrimary
            ),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text(
                text = "Get Started",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "Get Started Icon",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOnBoardingScreen() {
    MaterialTheme {
        OnBoardingScreen(
        )
    }
}
