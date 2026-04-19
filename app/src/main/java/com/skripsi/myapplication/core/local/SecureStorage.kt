package com.skripsi.myapplication.core.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Suppress("DEPRECATION")
@Singleton
class SecureStorage @Inject constructor(
    @ApplicationContext context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "auth_secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val _sessionExpiredEvent = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val sessionExpiredEvent = _sessionExpiredEvent.asSharedFlow()

    fun saveToken(token: String) {
        sharedPreferences.edit { putString(KEY_TOKEN, token) }
    }

    fun saveRefreshToken(refreshToken: String) {
        sharedPreferences.edit { putString(KEY_REFRESH_TOKEN, refreshToken) }
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
    }

    fun clearAuth() {
        sharedPreferences.edit {
            remove(KEY_TOKEN)
            remove(KEY_REFRESH_TOKEN)
        }
    }

    fun triggerSessionExpired() {
        _sessionExpiredEvent.tryEmit(Unit)
    }

    companion object {
        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_REFRESH_TOKEN = "jwt_refresh_token"
    }
}
