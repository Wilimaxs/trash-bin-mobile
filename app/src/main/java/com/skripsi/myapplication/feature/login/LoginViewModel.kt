package com.skripsi.myapplication.feature.login

import androidx.lifecycle.ViewModel
import com.skripsi.myapplication.utils.validation.EmailValidator
import com.skripsi.myapplication.utils.validation.PasswordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.skripsi.myapplication.utils.snackbar.CustomSnackBarManager

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

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

    fun onLoginClickWithCallback(onSuccess: () -> Unit) {
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
            //TODO: Add API Here When Ready
            CustomSnackBarManager.showSuccess("Login Successful")
            onSuccess()
        } else {
            //TODO: Handle When user wrong input
        }
    }
}
