package com.skripsi.myapplication.feature.mainNavigation

import com.skripsi.myapplication.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
) {
    object Home : BottomNavItem("nav_home", "Home", R.drawable.ic_home_active, R.drawable.ic_home)
    object History : BottomNavItem("nav_history", "History", R.drawable.ic_history_active, R.drawable.ic_history)
    object Profile : BottomNavItem("nav_profile", "Profile", R.drawable.ic_profile_active, R.drawable.ic_profile)
}

