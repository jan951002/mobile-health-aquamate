package com.poli.health.aquamate.onboarding.auth.data.error

internal interface FirebaseAuthErrorHandler {
    fun getErrorMessage(exception: Exception): String
}
