package com.skripsi.myapplication.core.network.interceptor

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProvider @Inject constructor() {

    // Placeholder: Nanti akan diubah menggunakan DataStore / SharedPreferences
    fun getToken(): String {
        return "123_ini_dummy_token_sementara"
    }

}

