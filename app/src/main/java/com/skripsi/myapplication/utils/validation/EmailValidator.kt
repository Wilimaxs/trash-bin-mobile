package com.skripsi.myapplication.utils.validation

object EmailValidator {
    fun validate(email: String): String? {
        if (email.isEmpty()) {
            return "Email cannot be empty"
        }

        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
        if (!email.matches(emailRegex)) {
            return "Please enter a valid email format"
        }

        return null
    }
}

