package com.skripsi.myapplication.repository

import com.skripsi.myapplication.core.local.SecureStorage
import com.skripsi.myapplication.core.network.ApiService
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.model.RegisterRequest
import com.skripsi.myapplication.model.RegisterResponse
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
                secureStorage.saveToken(accessToken)
            }
        }

        return result
    }
}

