package com.skripsi.myapplication.feature.onboarding


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skripsi.myapplication.R
import com.skripsi.myapplication.utils.composables.PrimaryButton

@Composable
fun OnBoardingScreen(
//    onGetStartedClick: () -> Unit,
//    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        OnboardingHeader()

        Spacer(modifier = Modifier.weight(1f))

        OnboardingContent()

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = "Get Started",
            onClick = {},
            modifier = Modifier.padding(horizontal = 24.dp),
            iconResId = R.drawable.ic_right_arrow,
            iconContentDescription = "Get Started Icon"
        )
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
