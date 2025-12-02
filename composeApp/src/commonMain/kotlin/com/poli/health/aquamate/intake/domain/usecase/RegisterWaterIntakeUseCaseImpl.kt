package com.poli.health.aquamate.intake.domain.usecase

import com.poli.health.aquamate.intake.data.repository.WaterIntakeRepository
import com.poli.health.aquamate.intake.domain.model.WaterIntake
import com.poli.health.aquamate.ui.theme.AquaMateStrings

internal class RegisterWaterIntakeUseCaseImpl(
    private val repository: WaterIntakeRepository
) : RegisterWaterIntakeUseCase {
    override suspend fun invoke(volumeMl: Int): Result<WaterIntake> {
        require(volumeMl > 0) { AquaMateStrings.Intake.VOLUME_MUST_BE_POSITIVE }
        require(volumeMl <= 2000) { AquaMateStrings.Intake.VOLUME_EXCEEDS_LIMIT }

        val intake = WaterIntake.create(volumeMl)
        return repository.registerIntake(intake)
    }
}
