package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.domain.model.WaterIntake

interface RegisterWaterIntakeUseCase {
    suspend operator fun invoke(volumeMl: Int): Result<WaterIntake>
}
