package com.poli.health.aquamate.intake.domain.usecase

import com.poli.health.aquamate.intake.domain.model.WaterIntake

interface RegisterWaterIntakeUseCase {
    suspend operator fun invoke(volumeMl: Int): Result<WaterIntake>
}
