package com.skripsi.myapplication.model

import com.google.gson.annotations.SerializedName

data class ConnectRequest(
    @SerializedName("qr_code")
    val qrCode: String
)

data class SessionData(
    @SerializedName("session_id")
    val sessionId: Int,
    @SerializedName("trash_bin_id")
    val trashBinId: Int,
    @SerializedName("qr_code")
    val qrCode: String,
    @SerializedName("total_points")
    val totalPoints: Int,
    @SerializedName("total_items")
    val totalItems: Int
)

data class StreamUpdateData(
    @SerializedName("is_connected")
    val isConnected: Boolean,
    @SerializedName("message")
    val message: String?,
    @SerializedName("bin_name")
    val binName: String?,
    @SerializedName("total_points")
    val totalPoints: Int?,
    @SerializedName("total_items")
    val totalItems: Int?,
    @SerializedName("capacity_organic")
    val capacityOrganic: Int?,
    @SerializedName("capacity_inorganic")
    val capacityInorganic: Int?,
    @SerializedName("capacity_b3")
    val capacityB3: Int?,
    @SerializedName("live_activity")
    val liveActivity: List<LiveActivity>?
)

data class LiveActivity(
    @SerializedName("category")
    val category: String,
    @SerializedName("compartment_type")
    val compartmentType: String,
    @SerializedName("points_earned")
    val pointsEarned: Int,
    @SerializedName("time")
    val time: String
)

