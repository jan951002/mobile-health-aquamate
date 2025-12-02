package com.poli.health.aquamate.intake.domain.usecase

import com.poli.health.aquamate.intake.data.repository.WaterIntakeRepository

internal class DeleteLastIntakeUseCaseImpl(
    private val repository: WaterIntakeRepository
) : DeleteLastIntakeUseCase {
    override suspend fun invoke(): Result<Unit> {
        return repository.deleteLastIntake()
    }
}
