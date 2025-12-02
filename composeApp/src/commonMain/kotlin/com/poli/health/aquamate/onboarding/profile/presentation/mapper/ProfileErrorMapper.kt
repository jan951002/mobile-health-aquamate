package com.poli.health.aquamate.onboarding.profile.presentation.mapper

import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileError

/**
 * Maps domain ProfileError to user-friendly messages.
 * TODO: Replace hardcoded English messages with string resources for i18n support.
 */
internal object ProfileErrorMapper {

    fun toMessage(error: ProfileError): String {
        return when (error) {
            // Validation errors
            is ProfileError.Validation.WeightTooLow -> "Weight is too low. Minimum is 20 kg"
            is ProfileError.Validation.WeightTooHigh -> "Weight is too high. Maximum is 300 kg"
            is ProfileError.Validation.WeightInvalid -> "Weight must be a positive number"
            is ProfileError.Validation.HeightTooLow -> "Height is too low. Minimum is 100 cm"
            is ProfileError.Validation.HeightTooHigh -> "Height is too high. Maximum is 250 cm"
            is ProfileError.Validation.HeightInvalid -> "Height must be a positive number"
            is ProfileError.Validation.AgeTooLow -> "Age is too low. Minimum is 18 years"
            is ProfileError.Validation.AgeTooHigh -> "Age is too high. Maximum is 120 years"
            is ProfileError.Validation.AgeInvalid -> "Age must be a positive number"
            is ProfileError.Validation.UserIdBlank -> "User ID is required"
            is ProfileError.Validation.BasalMetabolicRateInvalid -> "Invalid metabolic rate value"

            // Remote errors
            is ProfileError.Remote.PermissionDenied -> "Permission denied. Please check your access rights"
            is ProfileError.Remote.NetworkUnavailable -> "Network unavailable. Please check your connection"
            is ProfileError.Remote.UserNotAuthenticated -> "Please sign in again to continue"
            is ProfileError.Remote.DocumentNotFound -> "Profile not found"
            is ProfileError.Remote.Unknown -> "An unexpected error occurred. Please try again"

            // Local errors
            is ProfileError.Local.ReadFailed -> "Failed to read local data"
            is ProfileError.Local.WriteFailed -> "Failed to save local data"
            is ProfileError.Local.Unknown -> "A local storage error occurred"
        }
    }
}
