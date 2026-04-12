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

// Ekstensi untuk membuat DataStore instance yang terikat dengan Context
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@Singleton
class LocalStorage @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    // Contoh Flow untuk diobservasi oleh UI / ViewModel
    val hasSeenOnboarding: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_HAS_SEEN_ONBOARDING] ?: false
    }

    // Flow untuk mengobservasi data user (misal dalam bentuk JSON String)
    val userData: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[KEY_USER_DATA]
    }

    // Fungsi suspend untuk menyimpan data (secara asynchronous)
    suspend fun saveOnboardingState(hasSeen: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_HAS_SEEN_ONBOARDING] = hasSeen
        }
    }

    // Menyimpan data user setelah sukses login
    suspend fun saveUserData(userJson: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_DATA] = userJson
        }
    }

    // Menghapus data user (logout)
    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_USER_DATA)
        }
    }

    companion object {
        // Mendefinisikan key untuk nilai Boolean
        private val KEY_HAS_SEEN_ONBOARDING = booleanPreferencesKey("has_seen_onboarding")
        // Mendefinisikan key untuk menyimpan data user (String/JSON)
        private val KEY_USER_DATA = stringPreferencesKey("user_data")
    }
}
