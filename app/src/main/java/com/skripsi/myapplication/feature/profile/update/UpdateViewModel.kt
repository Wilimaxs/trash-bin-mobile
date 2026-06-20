package com.skripsi.myapplication.feature.profile.update

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.repository.UserRepository
import com.skripsi.myapplication.utils.extensions.toFullImageUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(EditProfileState())
    val state: StateFlow<EditProfileState> = _state.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            when (val result = userRepository.getProfile()) {
                is NetworkResult.Success -> {
                    val data = result.data
                    _state.update {
                        it.copy(
                            fullName = data.fullName,
                            email = data.email,
                            currentAvatarUrl = data.avatarUrl.toFullImageUrl(),
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _state.update { it.copy(errorMessage = result.message) }
                }

                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun onFullNameChange(name: String) {
        _state.update { it.copy(fullName = name, fullNameError = null) }
    }

    fun onAvatarSelected(uri: Uri?) {
        _state.update { it.copy(avatarUri = uri) }
    }

    fun onSaveChanges() {
        val current = _state.value

        // Validasi
        if (current.fullName.isBlank()) {
            _state.update { it.copy(fullNameError = "Full name cannot be empty") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            // Konversi fullName → RequestBody (plain text)
            val fullNameBody = current.fullName
                .toRequestBody("text/plain".toMediaTypeOrNull())

            val avatarPart = current.avatarUri?.toMultipartPart()

            when (val result = userRepository.updateProfile(fullNameBody, avatarPart)) {
                is NetworkResult.Success -> {
                    val data = result.data
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            fullName = data.fullName,
                            currentAvatarUrl = data.avatarUrl.toFullImageUrl(),
                            avatarUri = null
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }

                is NetworkResult.Loading -> Unit
            }
        }
    }

    private fun Uri.toMultipartPart(): MultipartBody.Part? {
        return try {
            val context = getApplication<Application>()
            val resolver = context.contentResolver
            val mimeType = resolver.getType(this) ?: "image/jpeg"
            val inputStream = resolver.openInputStream(this) ?: return null
            val bytes = inputStream.readBytes()
            inputStream.close()

            val requestBody = bytes.toRequestBody(mimeType.toMediaTypeOrNull())
            val extension = mimeType.substringAfterLast("/", "jpg")
            MultipartBody.Part.createFormData("avatar", "avatar.$extension", requestBody)
        } catch (_: Exception) {
            null
        }
    }
}