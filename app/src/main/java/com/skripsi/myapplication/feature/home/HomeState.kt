package com.skripsi.myapplication.feature.home

data class HomeState(
    val isConnected: Boolean = false,
    val rvmName: String = "EcoBin-RVM-Alpha",
    val connectedOn: String = "24 May 2024",
    val organicPercent: Int = 45,
    val anorganicPercent: Int = 12,
    val b3Percent: Int = 8,
    val totalItems: Int = 124,
    val totalPoints: Int = 86
)
