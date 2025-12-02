package com.poli.health.aquamate.intake.data.error

import com.poli.health.aquamate.intake.domain.model.IntakeError

internal class IntakeFirestoreErrorHandlerImpl : IntakeFirestoreErrorHandler {

    override fun mapError(exception: Exception): IntakeError.Remote {
        return when {
            exception.message?.contains("PERMISSION_DENIED", ignoreCase = true) == true -> {
                IntakeError.Remote.PermissionDenied
            }
            exception.message?.contains("NOT_FOUND", ignoreCase = true) == true -> {
                IntakeError.Remote.DocumentNotFound
            }
            exception.message?.contains("UNAUTHENTICATED", ignoreCase = true) == true -> {
                IntakeError.Remote.UserNotAuthenticated
            }
            exception.message?.contains("UNAVAILABLE", ignoreCase = true) == true -> {
                IntakeError.Remote.NetworkUnavailable
            }
            exception.message?.contains("network", ignoreCase = true) == true -> {
                IntakeError.Remote.NetworkUnavailable
            }
            else -> {
                IntakeError.Remote.Unknown
            }
        }
    }
}
