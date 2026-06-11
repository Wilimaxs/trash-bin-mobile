package com.skripsi.myapplication.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.myapplication.core.network.NetworkResult
import com.skripsi.myapplication.model.LoginRequest
import com.skripsi.myapplication.repository.AuthRepository
import com.skripsi.myapplication.utils.validation.EmailValidator
import com.skripsi.myapplication.utils.validation.PasswordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.skripsi.myapplication.utils.snackbar.CustomSnackBarManager

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.update { currentState ->
            currentState.copy(
                email = email,
                isEmailError = false,
                emailErrorMessage = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _state.update { currentState ->
            currentState.copy(
                password = password,
                isPasswordError = false,
                passwordErrorMessage = null
            )
        }
    }

    fun onLoginClickWithCallback(onSuccess: (String, String) -> Unit) {
        val currentState = _state.value

        val emailError = EmailValidator.validate(currentState.email)
        val passwordError = PasswordValidator.validate(currentState.password)

        _state.update {
            it.copy(
                isEmailError = emailError != null,
                emailErrorMessage = emailError,
                isPasswordError = passwordError != null,
                passwordErrorMessage = passwordError
            )
        }

        if (emailError == null && passwordError == null) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            viewModelScope.launch {
                val request = LoginRequest(
                    email = currentState.email,
                    password = currentState.password
                )

                when (val result = authRepository.login(request)) {
                    is NetworkResult.Success -> {
                        _state.update { it.copy(isLoading = false, isEmailError = false, isPasswordError = false) }
                        CustomSnackBarManager.showSuccess("Login Successful")
                        onSuccess(result.data.accessToken, result.data.refreshToken)
                    }
                    is NetworkResult.Error -> {
                        _state.update { it.copy(isLoading = false, errorMessage = result.message) }
                    }
                    is NetworkResult.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        } else {
            // Already handled error updates in state above
        }
    }
}
