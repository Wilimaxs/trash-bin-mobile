package com.skripsi.myapplication.model

import com.google.gson.annotations.SerializedName

data class PointEarnedResponse(
    @SerializedName("point_earned") val pointEarned: Int
)

data class PaginatedResponse<T>(
    @SerializedName("data") val data: List<T>,
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("total_pages") val totalPages: Int
)

data class HistoryItem(
    @SerializedName("id") val id: Int,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("points_earned") val pointsEarned: Int,
    @SerializedName("compartment_type") val compartmentType: String,
    @SerializedName("sub_category") val subCategory: String,
    @SerializedName("created_at") val createdAt: String
)

