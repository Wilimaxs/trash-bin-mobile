package com.skripsi.myapplication.feature.verify

data class VerifyState(
    val otpCode: String = "",
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val timerActive: Boolean = true,
    val timerSeconds: Int = 60,
    val isLoading: Boolean = false
) {
    val isOtpComplete: Boolean
        get() = otpCode.length == 6
}