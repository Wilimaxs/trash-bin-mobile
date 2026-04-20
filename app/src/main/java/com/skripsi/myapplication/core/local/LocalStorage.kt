package com.skripsi.myapplication.core.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@Singleton
class LocalStorage @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    val hasSeenOnboarding: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_HAS_SEEN_ONBOARDING] ?: false
    }

    val activeQrCode: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[KEY_ACTIVE_QR]
    }

    suspend fun saveOnboardingState(hasSeen: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_HAS_SEEN_ONBOARDING] = hasSeen
        }
    }

    suspend fun saveUserData(userJson: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_DATA] = userJson
        }
    }

    suspend fun saveActiveQrCode(qr: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_ACTIVE_QR] = qr
        }
    }

    suspend fun clearActiveQrCode() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_ACTIVE_QR)
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            val hasSeenOnboarding = preferences[KEY_HAS_SEEN_ONBOARDING]
            preferences.clear()
            if (hasSeenOnboarding != null) {
                preferences[KEY_HAS_SEEN_ONBOARDING] = hasSeenOnboarding
            }
        }
    }

    companion object {
        private val KEY_HAS_SEEN_ONBOARDING = booleanPreferencesKey("has_seen_onboarding")
        private val KEY_USER_DATA = stringPreferencesKey("user_data")
        private val KEY_ACTIVE_QR = stringPreferencesKey("active_qr_code")
    }
}
