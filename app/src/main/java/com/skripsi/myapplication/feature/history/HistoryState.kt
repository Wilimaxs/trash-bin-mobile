package com.skripsi.myapplication.feature.history

import com.skripsi.myapplication.model.HistoryItem

data class HistoryState(
    val totalPoints: Int = 0,
    val historyItems: List<HistoryItem> = emptyList(),
    val currentPage: Int = 1,
    val maxPage: Int = 1,
    val selectedCategory: String = "All",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val errorMessage: String? = null
) {
    val groupedHistory: Map<String, List<HistoryItem>>
        get() {

            return historyItems.groupBy { item ->
                when {
                    item.createdAt.contains("-04-") -> "April"
                    item.createdAt.contains("-09-") -> "September"
                    item.createdAt.contains("-10-") -> "October"
                    item.createdAt.contains("-11-") -> "November"
                    item.createdAt.contains("-12-") -> "December"
                    // format dari API: 2026-04-18T11:19:19.179756+00:00 -> ambil bulan ke 5..7
                    else -> try {
                        val parts = item.createdAt.split("-")
                        if (parts.size >= 2) {
                            val month = parts[1]
                            when(month) {
                                "01" -> "January"
                                "02" -> "February"
                                "03" -> "March"
                                "04" -> "April"
                                "05" -> "May"
                                "06" -> "June"
                                "07" -> "July"
                                "08" -> "August"
                                "09" -> "September"
                                "10" -> "October"
                                "11" -> "November"
                                "12" -> "December"
                                else -> "Older"
                            }
                        } else "Older"
                    } catch (_: Exception) {
                        "Older"
                    }
                }
            }
        }
}
