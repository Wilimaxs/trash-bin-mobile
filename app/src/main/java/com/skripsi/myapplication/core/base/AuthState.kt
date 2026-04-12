package com.skripsi.myapplication.core.base

sealed class AuthState {
    object Loading : AuthState()
    object Onboarding : AuthState()
    object Unauthenticated : AuthState()
    object Authenticated : AuthState()
}

