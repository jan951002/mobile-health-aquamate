package com.poli.health.aquamate.intake.domain.usecase

import com.poli.health.aquamate.intake.data.repository.WaterIntakeRepository
import com.poli.health.aquamate.intake.domain.model.WaterIntake

internal class GetLastIntakeUseCaseImpl(
    private val repository: WaterIntakeRepository
) : GetLastIntakeUseCase {
    override suspend fun invoke(): WaterIntake? {
        return repository.getLastIntake()
    }
}
