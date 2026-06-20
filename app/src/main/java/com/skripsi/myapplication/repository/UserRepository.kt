package com.skripsi.myapplication.repository

import com.skripsi.myapplication.core.network.ApiService
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.model.ProfileData
import com.skripsi.myapplication.model.UpdateProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService
): BaseRepository() {

    suspend fun getProfile(): NetworkResult<ProfileData> {
        return safeApiCall { api.getProfileData() }
    }

    suspend fun updateProfile(
        fullName: RequestBody,
        avatar: MultipartBody.Part?
    ): NetworkResult<UpdateProfileResponse> {
        return safeApiCall { api.updateProfile(fullName, avatar) }
    }

}