package com.skripsi.myapplication.model

import com.google.gson.annotations.SerializedName

data class HistoryItem(
    @SerializedName("id") val id: Int,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("points_earned") val pointsEarned: Int,
    @SerializedName("compartment_type") val compartmentType: String,
    @SerializedName("sub_category") val subCategory: String,
    @SerializedName("created_at") val createdAt: String
)

data class HistoryListData(
    @SerializedName("data") val records: List<HistoryItem>
)

data class PointsData(
    @SerializedName("point_earned") val pointEarned: Int
)
