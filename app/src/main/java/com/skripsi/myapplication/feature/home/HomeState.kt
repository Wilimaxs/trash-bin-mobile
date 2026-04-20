package com.skripsi.myapplication.feature.home

import com.skripsi.myapplication.model.LiveActivity

data class HomeState(
    val isConnected: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val qrCode: String? = null,
    val rvmName: String = "-",
    val connectedOn: String = "-",
    val organicPercent: Int = 0,
    val anorganicPercent: Int = 0,
    val b3Percent: Int = 0,
    val totalItems: Int = 0,
    val totalPoints: Int = 0,
    val liveActivities: List<LiveActivity> = emptyList()
)
