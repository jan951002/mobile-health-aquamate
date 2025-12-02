package com.poli.health.aquamate.onboarding.profile.domain.validator

import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileError
import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileValidationResult
import com.poli.health.aquamate.onboarding.profile.domain.model.UserProfile

internal class UserProfileValidatorImpl : UserProfileValidator {

    companion object {
        private const val MIN_WEIGHT_KG = 20.0
        private const val MAX_WEIGHT_KG = 300.0
        private const val MIN_HEIGHT_CM = 100.0
        private const val MAX_HEIGHT_CM = 250.0
        private const val MIN_AGE = 18
        private const val MAX_AGE = 120
    }

    override fun validate(profile: UserProfile): ProfileValidationResult {
        val errors = mutableMapOf<String, ProfileError.Validation>()

        when {
            profile.weightKg < MIN_WEIGHT_KG -> errors["weight"] = ProfileError.Validation.WeightTooLow
            profile.weightKg > MAX_WEIGHT_KG -> errors["weight"] = ProfileError.Validation.WeightTooHigh
        }

        when {
            profile.heightCm < MIN_HEIGHT_CM -> errors["height"] = ProfileError.Validation.HeightTooLow
            profile.heightCm > MAX_HEIGHT_CM -> errors["height"] = ProfileError.Validation.HeightTooHigh
        }

        when {
            profile.age < MIN_AGE -> errors["age"] = ProfileError.Validation.AgeTooLow
            profile.age > MAX_AGE -> errors["age"] = ProfileError.Validation.AgeTooHigh
        }

        return if (errors.isEmpty()) {
            ProfileValidationResult.Valid
        } else {
            ProfileValidationResult.Invalid(errors)
        }
    }
}
