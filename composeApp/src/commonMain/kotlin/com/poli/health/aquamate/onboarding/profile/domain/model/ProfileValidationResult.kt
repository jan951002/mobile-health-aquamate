package com.poli.health.aquamate.onboarding.profile.domain.model

/**
 * Result of user profile validation.
 * Uses error enums to support internationalization in the presentation layer.
 */
sealed class ProfileValidationResult {
    data object Valid : ProfileValidationResult()
    data class Invalid(val errors: Map<String, ProfileError.Validation>) : ProfileValidationResult()
}
