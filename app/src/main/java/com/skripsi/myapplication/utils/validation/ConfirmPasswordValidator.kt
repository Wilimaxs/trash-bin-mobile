package com.skripsi.myapplication.utils.validation

object ConfirmPasswordValidator {
    fun validate(password: String, confirmPassword: String): String? {
        if (confirmPassword.isEmpty()) {
            return "Please confirm your password"
        }

        if (password != confirmPassword) {
            return "Passwords do not match"
        }

        return null
    }
}

