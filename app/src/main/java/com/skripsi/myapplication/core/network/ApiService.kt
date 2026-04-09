package com.skripsi.myapplication.core.network

import com.skripsi.myapplication.model.ApiResponse
import com.skripsi.myapplication.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("user/profile")
    suspend fun getProfile(): Response<ApiResponse<User>>
}