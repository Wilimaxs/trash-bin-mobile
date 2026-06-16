package com.skripsi.myapplication.feature.profile.privacy


data class PrivacySection(
    val icon: Int,
    val title: String,
    val description: String,
    val bulletPoints: List<Pair<String, String>> = emptyList()
)

data class PrivacyState(
    val sections: List<PrivacySection> = emptyList(),
    val lastUpdated: String = "October 24, 2023",
    val contactEmail: String = "privacy@ecobin.com"
)
