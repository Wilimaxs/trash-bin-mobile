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

        val bodyMessage = response.body()?.message
        if (!bodyMessage.isNullOrEmpty()) return bodyMessage

        val errorBody = response.errorBody()?.string()
        if (!errorBody.isNullOrEmpty()) {
            try {
                val errorResponse = gson.fromJson(errorBody, ApiResponse::class.java)
                if (errorResponse.message.isNotEmpty()) return errorResponse.message
            } catch (_: Exception) {
            }
        }
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