package com.skripsi.myapplication.model

import com.google.gson.annotations.SerializedName

data class ProfileData(
    @SerializedName("full_name") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("member_since") val memberSince: String,
    @SerializedName("total_points") val totalPoints: Int,
    @SerializedName("total_items") val totalItems: Int
)

data class UpdateProfileResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("avatar_url") val avatarUrl: String?
)

