package com.poli.health.aquamate.onboarding.profile.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.model.ActivityLevel

interface CalculateWaterIntakeUseCase {
    operator fun invoke(weightKg: Double, activityLevel: ActivityLevel): Int
}
