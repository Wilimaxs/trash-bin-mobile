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

