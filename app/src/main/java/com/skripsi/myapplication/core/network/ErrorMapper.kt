package com.skripsi.myapplication.core.network

import com.google.gson.Gson
import com.skripsi.myapplication.model.ApiResponse
import retrofit2.Response

object ErrorMapper {

    private val gson = Gson()

    /**
     * Logic: Prioritize an API message, then Status Code, then Default Message.
     */
    fun <T> getApiMessage(response: Response<ApiResponse<T>>): String {
        val code = response.code()

        // 1. Coba ambil dari body (biasanya kalau response.isSuccessful tapi data null)
        val bodyMessage = response.body()?.message
        if (!bodyMessage.isNullOrEmpty()) return bodyMessage

        // 2. Coba ambil dari errorBody (untuk status 4xx atau 5xx)
        val errorBody = response.errorBody()?.string()
        if (!errorBody.isNullOrEmpty()) {
            try {
                // Parsing JSON error dari backend
                val errorResponse = gson.fromJson(errorBody, ApiResponse::class.java)
                if (errorResponse.message.isNotEmpty()) return errorResponse.message
            } catch (_: Exception) {
                // Jika gagal parsing, lanjut ke step berikutnya
            }
        }
        // 3. Fallback ke Mapping Status Code jika tidak ada message dari API
        return getMessageFromCode(code)
    }

    private fun getMessageFromCode(code: Int): String {
        return when (code) {
            400 -> "Invalid request. Please verify your data."
            401 -> "Your session has expired. Please log in again."
            403 -> "Access denied. You don't have permission."
            404 -> "The requested information was not found."
            408 -> "Request timeout. Please check your connection."
            422 -> "Validation error. Please check your input."
            429 -> "Too many requests. Please try again later."
            in 500..599 -> "Server is currently busy. Please try again later."
            else -> "An unexpected error occurred (Error: $code)"
        }
    }
}