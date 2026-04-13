package com.skripsi.myapplication.utils.validation

object FullNameValidator {
    fun validate(name: String): String? {
        val trimmedName = name.trim()

        if (trimmedName.isEmpty()) {
            return "Full name cannot be empty"
        }

        if (trimmedName.length < 3) {
            return "Full name must be at least 3 characters long"
        }

        val nameRegex = "^[a-zA-Z\\s]+$".toRegex()
        if (!trimmedName.matches(nameRegex)) {
            return "Full name can only contain letters and spaces"
        }

        return null // valid
    }
}

