package com.skripsi.myapplication.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState(isLoading = true))
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    init {
        loadData(isRefresh = false)
    }

    fun loadData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) {
                _state.update { it.copy(isRefreshing = true, errorMessage = null) }
            } else {
                _state.update { it.copy(isLoading = true, errorMessage = null) }
            }

            // 1. Fetch Today's Point
            when (val pointsResult = historyRepository.getPointEarned()) {
                is NetworkResult.Success -> {
                    _state.update { it.copy(totalPoints = pointsResult.data.pointEarned) }
                }
                is NetworkResult.Error -> {
                    _state.update { it.copy(errorMessage = pointsResult.message) }
                }
                else -> {}
            }

            // 2. Fetch History List
            fetchHistoryList(page = 1, isRefresh = isRefresh)
        }
    }

    private suspend fun fetchHistoryList(page: Int, isRefresh: Boolean) {
        val category = _state.value.selectedCategory

        when (val historyResult = historyRepository.getHistoryList(page = page, size = 20, category = category)) {
            is NetworkResult.Success -> {
                val newItems = historyResult.data.data
                _state.update {
                    it.copy(
                        historyItems = if (page == 1) newItems else it.historyItems + newItems,
                        currentPage = historyResult.data.page,
                        maxPage = historyResult.data.totalPages,
                        isLoading = false,
                        isRefreshing = false,
                        isLoadingMore = false
                    )
                }
            }
            is NetworkResult.Error -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        isLoadingMore = false,
                        errorMessage = historyResult.message
                    )
                }
            }
            else -> {}
        }
    }

    fun loadMore() {
        val currentState = _state.value
        if (!currentState.isLoading && !currentState.isLoadingMore && currentState.currentPage < currentState.maxPage) {
            _state.update { it.copy(isLoadingMore = true) }
            viewModelScope.launch {
                fetchHistoryList(page = currentState.currentPage + 1, isRefresh = false)
            }
        }
    }

    fun onCategorySelected(category: String) {
        if (_state.value.selectedCategory == category) return

        _state.update { it.copy(selectedCategory = category, isLoading = true, historyItems = emptyList()) }

        viewModelScope.launch {
            fetchHistoryList(page = 1, isRefresh = false)
        }
    }
}
