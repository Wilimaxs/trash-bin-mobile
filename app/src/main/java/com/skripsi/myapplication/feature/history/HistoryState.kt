package com.skripsi.myapplication.feature.history

import com.skripsi.myapplication.model.HistoryItem

data class HistoryState(
    val totalPoints: Int = 0,
    val historyItems: List<HistoryItem> = emptyList(),
    val selectedCategory: String = "All",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val groupedHistory: Map<String, List<HistoryItem>>
        get() {
            // Sederhananya kita filter dulu berdasarkan kategori
            val filtered = if (selectedCategory == "All") {
                historyItems
            } else {
                historyItems.filter { it.compartmentType.equals(selectedCategory, ignoreCase = true) }
            }

            // Lalu di-group, di implementasi API nyata bisa diparsing dari `createdAt`
            // Namun karena ini dummy, kita akan gunakan mock groupBy sederhana
            return filtered.groupBy { item ->
                when {
                    item.createdAt.contains("-04-") -> "This Month"
                    item.createdAt.contains("-09-") -> "September"
                    else -> "Older"
                }
            }
        }
}

