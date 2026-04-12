package com.skripsi.myapplication.route

sealed class Screen(val route: String) {
    object Loading : Screen("loading_screen")
    object Onboarding : Screen("onboarding_screen")
    object Login : Screen("login_screen")
    object Home : Screen("home_screen")
}

