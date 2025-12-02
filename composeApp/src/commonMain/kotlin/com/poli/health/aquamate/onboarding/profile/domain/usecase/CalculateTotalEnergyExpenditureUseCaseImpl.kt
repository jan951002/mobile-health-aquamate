package com.poli.health.aquamate.onboarding.profile.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.exception.ProfileException
import com.poli.health.aquamate.onboarding.profile.domain.model.ActivityLevel
import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileError

internal class CalculateTotalEnergyExpenditureUseCaseImpl : CalculateTotalEnergyExpenditureUseCase {

    override fun invoke(basalMetabolicRate: Double, activityLevel: ActivityLevel): Double {
        if (basalMetabolicRate <= 0) {
            throw ProfileException(ProfileError.Validation.BasalMetabolicRateInvalid)
        }
        return basalMetabolicRate * activityLevel.energyFactor
    }
}
