package com.skripsi.myapplication.core.network

import com.skripsi.myapplication.model.ApiResponse
import com.skripsi.myapplication.model.User
import com.skripsi.myapplication.model.RegisterRequest
import com.skripsi.myapplication.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/registration")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<RegisterResponse>>

    @GET("user/profile")
    suspend fun getProfile(): Response<ApiResponse<User>>
}