package com.poli.health.aquamate.onboarding.profile.domain.model

/**
 * Represents all possible errors in the user profile domain.
 * Each error type can be mapped to localized messages in the presentation layer.
 */
sealed class ProfileError {

    /**
     * Validation errors for user profile data
     */
    sealed class Validation : ProfileError() {
        data object WeightTooLow : Validation()
        data object WeightTooHigh : Validation()
        data object WeightInvalid : Validation()
        data object HeightTooLow : Validation()
        data object HeightTooHigh : Validation()
        data object HeightInvalid : Validation()
        data object AgeTooLow : Validation()
        data object AgeTooHigh : Validation()
        data object AgeInvalid : Validation()
        data object UserIdBlank : Validation()
        data object BasalMetabolicRateInvalid : Validation()
    }

    /**
     * Remote storage errors from Firestore
     */
    sealed class Remote : ProfileError() {
        data object PermissionDenied : Remote()
        data object NetworkUnavailable : Remote()
        data object UserNotAuthenticated : Remote()
        data object DocumentNotFound : Remote()
        data object Unknown : Remote()
    }

    /**
     * Local storage errors
     */
    sealed class Local : ProfileError() {
        data object ReadFailed : Local()
        data object WriteFailed : Local()
        data object Unknown : Local()
    }
}
