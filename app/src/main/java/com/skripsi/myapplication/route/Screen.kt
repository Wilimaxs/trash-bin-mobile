package com.skripsi.myapplication.route

sealed class Screen(val route: String) {
    object Loading : Screen("loading_screen")
    object Onboarding : Screen("onboarding_screen")
    object Login : Screen("login_screen")
    object Registration : Screen("registration_screen")
    object Verify : Screen("verify_screen")
    object VerifyForgot : Screen("verify_forgot_screen")
    object Home : Screen("home_screen")
    object Forgot : Screen("forgot_screen")
    object ResetPassword : Screen("reset_password_screen")
    object PrivacyPolicy : Screen("privacy_policy")
    object HelpSupport : Screen("help_support")
}
