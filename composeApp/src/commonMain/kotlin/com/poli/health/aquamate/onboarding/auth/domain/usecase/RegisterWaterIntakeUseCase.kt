package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.domain.model.WaterIntake
import com.poli.health.aquamate.onboarding.auth.data.repository.WaterIntakeRepository

class RegisterWaterIntakeUseCase(
    private val repository: WaterIntakeRepository
) {
    suspend operator fun invoke(volumeMl: Int): Result<WaterIntake> {
        if (volumeMl <= 0) {
            return Result.failure(IllegalArgumentException("El volumen debe ser mayor a 0"))
        }

        if (volumeMl > 2000) {
            return Result.failure(IllegalArgumentException("El volumen no puede exceder 2000ml"))
        }

        val intake = WaterIntake.create(volumeMl)
        return repository.registerIntake(intake)
    }
}