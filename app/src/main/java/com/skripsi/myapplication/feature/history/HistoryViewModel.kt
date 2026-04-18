package com.skripsi.myapplication.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.myapplication.model.ApiResponse
import com.skripsi.myapplication.model.HistoryItem
import com.skripsi.myapplication.model.HistoryListData
import com.skripsi.myapplication.model.PointsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(HistoryState(isLoading = true))
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    init {
        fetchHistoryData()
    }

    private fun fetchHistoryData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(1000) // Simulasi hit API (loading delay)

            // 1. API fetch point today
            val mockPointsResponse = ApiResponse(
                status = true,
                message = "Success retrieve today's point earned",
                data = PointsData(pointEarned = 725)
            )

            // 2. API fetch list history (contoh param page=1 & type=Category)
            val mockHistoryResponse = ApiResponse(
                status = true,
                message = "Success retrieve user history",
                data = HistoryListData(
                    records = listOf(
                        HistoryItem(
                            id = 1,
                            imageUrl = null,
                            pointsEarned = 1,
                            compartmentType = "organik",
                            subCategory = "Kulit Pisang",
                            createdAt = "2026-04-24T14:30:00.000Z"
                        ),
                        HistoryItem(
                            id = 2,
                            imageUrl = null,
                            pointsEarned = 1,
                            compartmentType = "anorganik",
                            subCategory = "Botol Plastik",
                            createdAt = "2026-04-22T09:15:00.000Z"
                        ),
                        HistoryItem(
                            id = 3,
                            imageUrl = null,
                            pointsEarned = 1,
                            compartmentType = "B3",
                            subCategory = "Baterai Bekas",
                            createdAt = "2026-04-20T13:45:00.000Z"
                        ),
                        HistoryItem(
                            id = 4,
                            imageUrl = null,
                            pointsEarned = 1,
                            compartmentType = "organik",
                            subCategory = "Daun Kering",
                            createdAt = "2026-09-28T18:00:00.000Z"
                        ),
                        HistoryItem(
                            id = 5,
                            imageUrl = null,
                            pointsEarned = 1,
                            compartmentType = "anorganik",
                            subCategory = "Botol Kaca",
                            createdAt = "2026-09-25T11:10:00.000Z"
                        )
                    )
                )
            )

            _state.update {
                it.copy(
                    isLoading = false,
                    totalPoints = mockPointsResponse.data?.pointEarned ?: 0,
                    historyItems = mockHistoryResponse.data?.records ?: emptyList()
                )
            }
        }
    }

    fun onCategorySelected(category: String) {
        _state.update { it.copy(selectedCategory = category) }
        // TODO: Saat API sudah siap, jalankan hit API baru dengan `category` sebagai parameter type di sini.
        // fetchHistoryListByCategory(category)
    }
}
