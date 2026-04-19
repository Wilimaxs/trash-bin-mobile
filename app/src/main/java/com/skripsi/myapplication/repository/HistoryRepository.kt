package com.skripsi.myapplication.repository

import com.skripsi.myapplication.core.network.ApiService
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.model.HistoryItem
import com.skripsi.myapplication.model.PaginatedResponse
import com.skripsi.myapplication.model.PointEarnedResponse
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val api: ApiService
) : BaseRepository() {

    suspend fun getPointEarned(): NetworkResult<PointEarnedResponse> {
        return safeApiCall { api.getPointEarned() }
    }

    suspend fun getHistoryList(
        page: Int,
        size: Int,
        category: String
    ): NetworkResult<PaginatedResponse<HistoryItem>> {
        // Map UI category to API parameter
        val typeParam = when (category) {
            "Organik" -> "organic"
            "Anorganik" -> "inorganic"
            "B3" -> "b3"
            else -> null // "All" or unknown
        }

        return safeApiCall { api.getHistory(page = page, size = size, type = typeParam) }
    }
}

