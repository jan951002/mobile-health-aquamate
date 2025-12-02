package com.poli.health.aquamate.onboarding.profile.presentation.mapper

import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileError
import com.poli.health.aquamate.ui.theme.AquaMateStrings

/**
 * Maps domain ProfileError to user-friendly messages using string resources.
 */
internal object ProfileErrorMapper {

    fun toMessage(error: ProfileError): String {
        return when (error) {
            is ProfileError.Validation.WeightTooLow -> AquaMateStrings.Profile.Errors.WEIGHT_TOO_LOW
            is ProfileError.Validation.WeightTooHigh -> AquaMateStrings.Profile.Errors.WEIGHT_TOO_HIGH
            is ProfileError.Validation.WeightInvalid -> AquaMateStrings.Profile.Errors.WEIGHT_INVALID
            is ProfileError.Validation.HeightTooLow -> AquaMateStrings.Profile.Errors.HEIGHT_TOO_LOW
            is ProfileError.Validation.HeightTooHigh -> AquaMateStrings.Profile.Errors.HEIGHT_TOO_HIGH
            is ProfileError.Validation.HeightInvalid -> AquaMateStrings.Profile.Errors.HEIGHT_INVALID
            is ProfileError.Validation.AgeTooLow -> AquaMateStrings.Profile.Errors.AGE_TOO_LOW
            is ProfileError.Validation.AgeTooHigh -> AquaMateStrings.Profile.Errors.AGE_TOO_HIGH
            is ProfileError.Validation.AgeInvalid -> AquaMateStrings.Profile.Errors.AGE_INVALID
            is ProfileError.Validation.UserIdBlank -> AquaMateStrings.Profile.Errors.USER_ID_BLANK
            is ProfileError.Validation.BasalMetabolicRateInvalid -> AquaMateStrings.Profile.Errors.BMR_INVALID
            is ProfileError.Remote.PermissionDenied -> AquaMateStrings.Profile.Errors.PERMISSION_DENIED
            is ProfileError.Remote.NetworkUnavailable -> AquaMateStrings.Profile.Errors.NETWORK_UNAVAILABLE
            is ProfileError.Remote.UserNotAuthenticated -> AquaMateStrings.Profile.Errors.USER_NOT_AUTHENTICATED
            is ProfileError.Remote.DocumentNotFound -> AquaMateStrings.Profile.Errors.DOCUMENT_NOT_FOUND
            is ProfileError.Remote.Unknown -> AquaMateStrings.Profile.Errors.REMOTE_UNKNOWN
            is ProfileError.Local.ReadFailed -> AquaMateStrings.Profile.Errors.LOCAL_READ_FAILED
            is ProfileError.Local.WriteFailed -> AquaMateStrings.Profile.Errors.LOCAL_WRITE_FAILED
            is ProfileError.Local.Unknown -> AquaMateStrings.Profile.Errors.LOCAL_UNKNOWN
        }
    }
}
