package com.poli.health.aquamate.onboarding.auth.data.error

internal class FirebaseAuthErrorHandlerImpl : FirebaseAuthErrorHandler {

    override fun getErrorMessage(exception: Exception): String {
        return exception.message ?: "Unknown authentication error"
    }
}
