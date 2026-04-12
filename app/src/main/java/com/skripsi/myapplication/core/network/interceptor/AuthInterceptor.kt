package com.skripsi.myapplication.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import com.skripsi.myapplication.core.local.SecureStorage

class AuthInterceptor @Inject constructor(
    private val authPreferences: SecureStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = authPreferences.getToken()

        val requestBuilder = request.newBuilder()

        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}
