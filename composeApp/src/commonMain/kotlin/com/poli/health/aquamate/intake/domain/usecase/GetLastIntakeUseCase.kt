package com.poli.health.aquamate.intake.domain.usecase

import com.poli.health.aquamate.intake.domain.model.WaterIntake

interface GetLastIntakeUseCase {
    suspend operator fun invoke(): WaterIntake?
}
