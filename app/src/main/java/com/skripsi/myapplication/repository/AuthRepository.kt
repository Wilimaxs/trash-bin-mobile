package com.skripsi.myapplication.repository

import com.skripsi.myapplication.core.local.SecureStorage
import com.skripsi.myapplication.core.network.ApiService
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.model.RegisterRequest
import com.skripsi.myapplication.model.RegisterResponse
import com.skripsi.myapplication.model.VerifyRequest
import com.skripsi.myapplication.model.ForgotPasswordRequest
import com.skripsi.myapplication.model.ForgotPasswordResponse
import com.skripsi.myapplication.model.LoginRequest
import com.skripsi.myapplication.model.LoginResponse
import com.skripsi.myapplication.model.VerifyForgotResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val secureStorage: SecureStorage
) : BaseRepository() {

    suspend fun register(request: RegisterRequest): NetworkResult<RegisterResponse> {
        val result = safeApiCall { api.register(request) }

        if (result is NetworkResult.Success) {
            // Save token temporarily for OTP verification
            val accessToken = result.data.accessToken
            if (accessToken.isNotEmpty()) {
                secureStorage.saveTempVerifyToken(accessToken)
            }
        }

        return result
    }

    suspend fun verifyOtp(otpCode: String): NetworkResult<Any?> {
        val accessToken = secureStorage.getTempVerifyToken() ?: return NetworkResult.Error("Access token not found. Please try registering again.")

        val request = VerifyRequest(
            accessToken = accessToken,
            otpCode = otpCode
        )

        val result = safeApiCall { api.verifyOtp(request) }

        if (result is NetworkResult.Success) {
            secureStorage.clearTempVerifyToken()
        }

        return result
    }

    suspend fun verifyOtpForgot(otpCode: String): NetworkResult<VerifyForgotResponse> {
        val accessToken = secureStorage.getTempVerifyToken()
            ?: return NetworkResult.Error("Access token not found. Please try again.")

        val request = VerifyRequest(
            accessToken = accessToken,
            otpCode = otpCode
        )

        val result = safeApiCall { api.verifyForgotOtp(request) }

        if (result is NetworkResult.Success) {
            secureStorage.clearTempVerifyToken()
            secureStorage.saveTempResetToken(result.data.resetToken)
        }

        return result
    }

    suspend fun forgotPassword(request: ForgotPasswordRequest): NetworkResult<ForgotPasswordResponse> {
        val result = safeApiCall { api.forgotPassword(request) }

        if (result is NetworkResult.Success) {
            val accessToken = result.data.accessToken
            if (accessToken.isNotEmpty()) {
                secureStorage.saveTempVerifyToken(accessToken)
            }
        }

        return result
    }

    suspend fun login(request: LoginRequest): NetworkResult<LoginResponse> {
        val result = safeApiCall { api.login(request) }

        if (result is NetworkResult.Success) {
            val accessToken = result.data.accessToken
            val refreshToken = result.data.refreshToken

            if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
                secureStorage.saveToken(accessToken)
                secureStorage.saveRefreshToken(refreshToken)
            }
        }

        return result
    }

    suspend fun logout(): NetworkResult<Any?> {
        return safeApiCall { api.logout() }
    }
}
