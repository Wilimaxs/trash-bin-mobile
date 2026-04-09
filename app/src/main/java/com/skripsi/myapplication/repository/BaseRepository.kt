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
                    // Pastikan body tidak null dan data tidak null
                    if (body != null && body.data != null) {
                        NetworkResult.Success(body.data)
                    } else {
                        // Jika server kirim sukses tapi datanya null
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
                // Menangani error koneksi (no internet, timeout)
                NetworkResult.Error(
                    message = "Connection failed. Please check your internet connection.",
                    code = 0,
                    throwable = e
                )
            } catch (e: Exception) {
                // Menangani runtime error lainnya
                NetworkResult.Error(
                    message = e.localizedMessage ?: "An unexpected error occurred.",
                    code = null,
                    throwable = e
                )
            }
        }
    }

}