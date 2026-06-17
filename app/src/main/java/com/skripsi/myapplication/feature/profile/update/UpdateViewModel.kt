package com.skripsi.myapplication.feature.profile.update

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(
        EditProfileState(
            // TODO: Nanti  dari response API / datastore
            fullName = "Alex Johnson",
            email = "alex.j@example.com",
            phoneNumber = "+1 (555) 000-0000"
        )
    )
    val state: StateFlow<EditProfileState> = _state.asStateFlow()

    fun onFullNameChange(name: String) {
        _state.update {
            it.copy(
                fullName = name,
                fullNameError = null
            )
        }
    }

    fun onAvatarSelected(uri: Uri?) {
        _state.update { it.copy(avatarUri = uri) }
    }

    fun onSaveChanges() {
        val current = _state.value

        if (current.fullName.isBlank()) {
            _state.update { it.copy(fullNameError = "Full name cannot be empty") }
            return
        }

        // TODO: Hit API update profile di sini
    }
}