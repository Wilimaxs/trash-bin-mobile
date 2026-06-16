package com.skripsi.myapplication.feature.profile.privacy

import androidx.lifecycle.ViewModel
import com.skripsi.myapplication.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PrivacyViewmodel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(
        PrivacyState(
            sections = listOf(
                PrivacySection(
                    icon = R.drawable.ic_introduction,
                    title = "Introduction",
                    description = "At EcoBin, we are committed to protecting your privacy. This Privacy Policy explains how we collect, use, and safeguard your information when you use our smart trash bin platform and recycling services. By using the EcoBin app, you agree to the terms outlined in this policy."
                ),
                PrivacySection(
                    icon = R.drawable.ic_information,
                    title = "Information We Collect",
                    description = "We may collect the following types of information:",
                    bulletPoints = listOf(
                        "Personal Data" to "Name, email address, and profile picture when you create an account.",
                        "Usage Data" to "Recycling history, points earned, and interaction with Smart RVM units.",
                        "Location Data" to "Approximate location to help you find nearby recycling bins."
                    )
                ),
                PrivacySection(
                    icon = R.drawable.ic_consume,
                    title = "How We Use Your Data",
                    description = "Your data is primarily used to enhance your recycling experience. We use it to manage your account, track your eco-points, provide personalized recycling insights, and improve the functionality of our Smart Trash Bin RVM network. We do not sell your personal data to third parties."
                ),
                PrivacySection(
                    icon = R.drawable.ic_security,
                    title = "Data Security",
                    description = "We implement industry-standard security measures to protect your personal information from unauthorized access, alteration, disclosure, or destruction. While no internet transmission is completely secure, we strive to use commercially acceptable means to protect your personal data."
                )
            )
        )
    )
    val uiState: StateFlow<PrivacyState> = _uiState.asStateFlow()
}
