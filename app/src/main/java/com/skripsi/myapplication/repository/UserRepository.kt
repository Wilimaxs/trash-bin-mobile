package com.skripsi.myapplication.repository

import com.skripsi.myapplication.core.network.ApiService
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.model.ProfileData
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService
): BaseRepository() {
    suspend fun getProfile(): NetworkResult<ProfileData> {
        return safeApiCall { api.getProfileData() }
    }
}