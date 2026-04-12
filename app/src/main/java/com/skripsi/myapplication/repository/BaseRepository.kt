package com.skripsi.myapplication.repository

import com.skripsi.myapplication.core.network.ErrorMapper
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<ApiResponse<T>>
    ): NetworkResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.data != null) {
                        NetworkResult.Success(body.data)
                    } else {
                        NetworkResult.Error(
                            message = ErrorMapper.getApiMessage(response),
                            code = response.code()
                        )
                    }
                } else {
                    NetworkResult.Error(
                        message = ErrorMapper.getApiMessage(response),
                        code = response.code()
                    )
                }
            } catch (e: java.io.IOException) {
                NetworkResult.Error(
                    message = "Connection failed. Please check your internet connection.",
                    code = 0,
                    throwable = e
                )
            } catch (e: Exception) {
                NetworkResult.Error(
                    message = e.localizedMessage ?: "An unexpected error occurred.",
                    code = null,
                    throwable = e
                )
            }
        }
    }

}