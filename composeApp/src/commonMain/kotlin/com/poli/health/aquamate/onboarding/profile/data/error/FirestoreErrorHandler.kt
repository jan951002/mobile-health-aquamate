package com.poli.health.aquamate.onboarding.profile.data.error

import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileError

/**
 * Handles Firestore exceptions and maps them to domain errors.
 * This allows the presentation layer to handle localization.
 */
internal interface FirestoreErrorHandler {
    fun mapError(exception: Exception): ProfileError.Remote
}
