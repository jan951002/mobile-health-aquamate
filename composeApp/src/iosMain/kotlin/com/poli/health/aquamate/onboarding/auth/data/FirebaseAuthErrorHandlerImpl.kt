package com.poli.health.aquamate.onboarding.auth.data

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
internal class FirebaseAuthErrorHandlerImpl : FirebaseAuthErrorHandler {

    override fun getErrorMessage(error: NSError): String {
        return when (error.code) {
            17008L -> "Invalid email address"
            17009L -> "Incorrect password"
            17011L -> "No account found with this email"
            17005L -> "This account has been disabled"
            17010L -> "Too many attempts. Please try again later"
            17006L -> "Operation not allowed"
            17004L -> "Invalid credentials"
            else -> error.localizedDescription ?: "Unknown authentication error"
        }
    }
}
