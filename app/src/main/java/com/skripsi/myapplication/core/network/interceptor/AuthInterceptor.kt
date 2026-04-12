package com.skripsi.myapplication.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = tokenProvider.getToken()

        // Buat builder baru untuk memodifikasi request
        val requestBuilder = request.newBuilder()

        // Jika token tersedia, sisipkan ke dalam header Authorization
        if (token.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        // Lanjutkan request baik ada token maupun tidak
        return chain.proceed(requestBuilder.build())
    }
}

