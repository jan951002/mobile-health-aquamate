package com.poli.health.aquamate.onboarding.profile.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.model.ActivityLevel

interface CalculateTotalEnergyExpenditureUseCase {
    operator fun invoke(basalMetabolicRate: Double, activityLevel: ActivityLevel): Double
}
