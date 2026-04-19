package com.skripsi.myapplication.core.network.interceptor

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.skripsi.myapplication.BuildConfig
import com.skripsi.myapplication.core.local.SecureStorage
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Provider

class TokenAuthenticator @Inject constructor(
    private val secureStorageProvider: Provider<SecureStorage>
) : Authenticator {

    private val gson = Gson()

    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        val secureStorage = secureStorageProvider.get()
        val currentToken = secureStorage.getToken()
        val refreshToken = secureStorage.getRefreshToken()

        // Jika tidak ada token (sedang tidak login) atau tidak ada refresh token
        if (currentToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
            return null
        }

        // Jika request terakhir sudah menggunakan Bearer token yang BERBEDA dengan token di lokal
        // (contohnya: request ini antri, lalu ada proses refresh token lain yang sudah jalan dan mengubah token di local)
        val authHeader = response.request.header("Authorization")
        if (authHeader != null && !authHeader.contains(currentToken)) {
            return response.request.newBuilder()
                .header("Authorization", "Bearer $currentToken")
                .build()
        }

        synchronized(this) {
            val newTokenFromStorage = secureStorage.getToken() ?: return null

            if (currentToken != newTokenFromStorage) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $newTokenFromStorage")
                    .build()
            }

            val newAccessToken = refreshAuthToken(refreshToken)

            return if (newAccessToken != null) {
                secureStorage.saveToken(newAccessToken.first)
                secureStorage.saveRefreshToken(newAccessToken.second)

                response.request.newBuilder()
                    .header("Authorization", "Bearer ${newAccessToken.first}")
                    .build()
            } else {
                // Refresh token gagal (misal expired), log out otomatis
                secureStorage.clearAuth()
                secureStorage.triggerSessionExpired() // Lemparkan event logout ke View Model
                null
            }
        }
    }

    private fun refreshAuthToken(refreshToken: String): Pair<String, String>? {
        return try {
            val refreshClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .build()

            val jsonBody = """{"refresh_token": "$refreshToken"}"""
            val requestBody = jsonBody.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val request = Request.Builder()
                .url("${BuildConfig.BASE_URL}auth/refresh-token")
                .post(requestBody)
                .build()

            val response = refreshClient.newCall(request).execute()

            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                    val apiResponse = gson.fromJson(responseBody, RefreshDataResponse::class.java)
                    if (apiResponse.status && apiResponse.data != null) {
                        return Pair(apiResponse.data.accessToken, apiResponse.data.refreshToken)
                    }
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // DTO Internal untuk parsing JSON refresh token
    private data class RefreshDataResponse(
        @SerializedName("status") val status: Boolean,
        @SerializedName("message") val message: String,
        @SerializedName("data") val data: TokenData?
    )

    private data class TokenData(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("refresh_token") val refreshToken: String,
        @SerializedName("token_type") val tokenType: String
    )
}
