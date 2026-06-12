package com.skripsi.myapplication.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String,
    @SerializedName("full_name") val fullName: String
)

data class RegisterResponse(
    @SerializedName("access_token") val accessToken: String
)

data class VerifyRequest(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("otp_code") val otpCode: String
)

data class ForgotPasswordRequest(
    @SerializedName("email") val email: String
)

data class ForgotPasswordResponse(
    @SerializedName("access_token") val accessToken: String
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class LoginResponse(
    @SerializedName("user") val user: AuthUser,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("token_type") val tokenType: String
)

data class VerifyForgotResponse(
    @SerializedName("reset_token") val resetToken: String
)

data class AuthUser(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("total_points") val totalPoints: Int
)
