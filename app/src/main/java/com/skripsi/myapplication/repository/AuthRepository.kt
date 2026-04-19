package com.skripsi.myapplication.repository

import com.skripsi.myapplication.core.local.SecureStorage
import com.skripsi.myapplication.core.network.ApiService
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.model.RegisterRequest
import com.skripsi.myapplication.model.RegisterResponse
import com.skripsi.myapplication.model.VerifyRequest
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
}
