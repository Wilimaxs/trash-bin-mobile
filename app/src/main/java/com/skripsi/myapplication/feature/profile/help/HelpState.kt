package com.skripsi.myapplication.feature.profile.help

data class HelpSupportState(
    val searchQuery: String = "",
    val expandedItemId: Int? = null,
    val faqItems: List<FaqItem> = defaultFaqItems()
) {
    val filteredFaqItems: List<FaqItem>
        get() = if (searchQuery.isBlank()) {
            faqItems
        } else {
            faqItems.filter {
                it.question.contains(searchQuery, ignoreCase = true) ||
                        it.answer.contains(searchQuery, ignoreCase = true)
            }
        }
}

data class FaqItem(
    val id: Int,
    val question: String,
    val answer: String
)

fun defaultFaqItems() = listOf(
    FaqItem(
        id = 1,
        question = "How do I convert my points to digital money?",
        answer = "Go to the Home tab, tap Redeem, and select your e-wallet."
    ),
    FaqItem(
        id = 2,
        question = "What items can I recycle?",
        answer = "You can recycle plastic bottles, aluminum cans, and glass bottles at our RVM locations."
    ),
    FaqItem(
        id = 3,
        question = "How do I find a nearby RVM?",
        answer = "Open the Map tab in the app to find the nearest Reverse Vending Machine locations near you."
    ),
    FaqItem(
        id = 4,
        question = "What should I do if the RVM is full?",
        answer = "If the RVM is full, please try another nearby location or check back later."
    ),
    FaqItem(
        id = 5,
        question = "How to update my profile information?",
        answer = "Go to the Profile tab and tap Edit Profile to update your personal information."
    )
)