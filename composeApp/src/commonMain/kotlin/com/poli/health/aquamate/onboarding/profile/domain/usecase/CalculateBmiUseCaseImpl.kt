package com.poli.health.aquamate.onboarding.profile.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.exception.ProfileException
import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileError

internal class CalculateBmiUseCaseImpl : CalculateBmiUseCase {

    override fun invoke(weightKg: Double, heightCm: Double): Double {
        if (weightKg <= 0) {
            throw ProfileException(ProfileError.Validation.WeightInvalid)
        }
        if (heightCm <= 0) {
            throw ProfileException(ProfileError.Validation.HeightInvalid)
        }

        val heightM = heightCm / 100.0
        return weightKg / (heightM * heightM)
    }
}
