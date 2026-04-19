package com.skripsi.myapplication.core.network

import com.skripsi.myapplication.model.ApiResponse
import com.skripsi.myapplication.model.RegisterRequest
import com.skripsi.myapplication.model.RegisterResponse
import com.skripsi.myapplication.model.VerifyRequest
import com.skripsi.myapplication.model.LoginRequest
import com.skripsi.myapplication.model.LoginResponse
import com.skripsi.myapplication.model.ProfileData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/registration")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<RegisterResponse>>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<LoginResponse>>

    @POST("auth/verify")
    suspend fun verifyOtp(@Body request: VerifyRequest): Response<ApiResponse<Any?>>

    @GET("user/profile")
    suspend fun getProfileData(): Response<ApiResponse<ProfileData>>
}