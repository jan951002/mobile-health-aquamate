package com.poli.health.aquamate.onboarding.auth.data

import com.google.firebase.auth.FirebaseAuthException

internal class FirebaseAuthErrorHandlerImpl : FirebaseAuthErrorHandler {

    override fun getErrorMessage(
        exception: FirebaseAuthException
    ): String = when (exception.errorCode) {
        "ERROR_INVALID_EMAIL" -> "Invalid email address"
        "ERROR_WRONG_PASSWORD" -> "Incorrect password"
        "ERROR_USER_NOT_FOUND" -> "No account found with this email"
        "ERROR_USER_DISABLED" -> "This account has been disabled"
        "ERROR_TOO_MANY_REQUESTS" -> "Too many attempts. Please try again later"
        "ERROR_OPERATION_NOT_ALLOWED" -> "Operation not allowed"
        "ERROR_INVALID_CREDENTIAL" -> "Invalid credentials"
        else -> "Authentication error: ${exception.message}"
    }
}
