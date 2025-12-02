package com.poli.health.aquamate.onboarding.profile.data.error

import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileError

internal class FirestoreErrorHandlerImpl : FirestoreErrorHandler {

    override fun mapError(exception: Exception): ProfileError.Remote {
        return when {
            exception.message?.contains("PERMISSION_DENIED", ignoreCase = true) == true -> {
                ProfileError.Remote.PermissionDenied
            }
            exception.message?.contains("NOT_FOUND", ignoreCase = true) == true -> {
                ProfileError.Remote.DocumentNotFound
            }
            exception.message?.contains("UNAUTHENTICATED", ignoreCase = true) == true -> {
                ProfileError.Remote.UserNotAuthenticated
            }
            exception.message?.contains("UNAVAILABLE", ignoreCase = true) == true -> {
                ProfileError.Remote.NetworkUnavailable
            }
            exception.message?.contains("network", ignoreCase = true) == true -> {
                ProfileError.Remote.NetworkUnavailable
            }
            else -> {
                ProfileError.Remote.Unknown
            }
        }
    }
}
