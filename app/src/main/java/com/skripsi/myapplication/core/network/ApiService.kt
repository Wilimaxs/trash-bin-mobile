package com.skripsi.myapplication.core.network

import com.skripsi.myapplication.model.ApiResponse
import com.skripsi.myapplication.model.RegisterRequest
import com.skripsi.myapplication.model.RegisterResponse
import com.skripsi.myapplication.model.VerifyRequest
import com.skripsi.myapplication.model.LoginRequest
import com.skripsi.myapplication.model.LoginResponse
import com.skripsi.myapplication.model.ProfileData
import com.skripsi.myapplication.model.PointEarnedResponse
import com.skripsi.myapplication.model.PaginatedResponse
import com.skripsi.myapplication.model.HistoryItem
import com.skripsi.myapplication.model.ConnectRequest
import com.skripsi.myapplication.model.SessionData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("auth/registration")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<RegisterResponse>>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<LoginResponse>>

    @POST("auth/verify")
    suspend fun verifyOtp(@Body request: VerifyRequest): Response<ApiResponse<Any?>>

    @GET("user/profile")
    suspend fun getProfileData(): Response<ApiResponse<ProfileData>>

    @GET("history/point-earned")
    suspend fun getPointEarned(): Response<ApiResponse<PointEarnedResponse>>

    @GET("history")
    suspend fun getHistory(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: String? = null
    ): Response<ApiResponse<PaginatedResponse<HistoryItem>>>

    @POST("sessions/connect")
    suspend fun connectSession(@Body request: ConnectRequest): Response<ApiResponse<SessionData>>

    @POST("sessions/disconnect")
    suspend fun disconnectSession(@Body request: ConnectRequest): Response<ApiResponse<Any?>>
}