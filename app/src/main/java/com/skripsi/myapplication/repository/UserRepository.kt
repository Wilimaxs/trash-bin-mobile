package com.skripsi.myapplication.repository

import com.skripsi.myapplication.core.network.ApiService
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService
): BaseRepository() {
    suspend fun fetchStatus(): NetworkResult<User> {
        return safeApiCall { api.getProfile() }
    }
}