package com.skripsi.myapplication.feature.profile.help

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HelpSupportViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(HelpSupportState())
    val state: StateFlow<HelpSupportState> = _state.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query, expandedItemId = null) }
    }

    fun onFaqItemClick(itemId: Int) {
        _state.update { currentState ->
            val newExpandedId = if (currentState.expandedItemId == itemId) null else itemId
            currentState.copy(expandedItemId = newExpandedId)
        }
    }
}