package com.poli.health.aquamate.onboarding.profile.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.exception.ProfileException
import com.poli.health.aquamate.onboarding.profile.domain.model.ActivityLevel
import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileError

internal class CalculateWaterIntakeUseCaseImpl : CalculateWaterIntakeUseCase {

    override fun invoke(weightKg: Double, activityLevel: ActivityLevel): Int {
        if (weightKg <= 0) {
            throw ProfileException(ProfileError.Validation.WeightInvalid)
        }
        return (weightKg * activityLevel.waterFactorMlPerKg).toInt()
    }
}
