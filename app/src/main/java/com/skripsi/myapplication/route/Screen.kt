package com.skripsi.myapplication.route

sealed class Screen(val route: String) {
    object Loading : Screen("loading_screen")
    object Onboarding : Screen("onboarding_screen")
    object Login : Screen("login_screen")
    object Registration : Screen("registration_screen")
    object Verify : Screen("verify_screen")
    object Home : Screen("home_screen")
    object Forgot : Screen("forgot_screen")
}
