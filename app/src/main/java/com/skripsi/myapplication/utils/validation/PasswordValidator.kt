package com.skripsi.myapplication.utils.validation

object PasswordValidator {
    fun validate(password: String): String? {
        if (password.isEmpty()) {
            return "Password cannot be empty"
        }

        if (password.length < 8) {
            return "Password must be at least 8 characters long"
        }

        // Opsional: validation uppercase
        // val hasUpperCase = password.any { it.isUpperCase() }
        // if (!hasUpperCase) return "Password must contain uppercase letters"

        return null
    }
}

