package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.WaterIntakeRepository

internal class DeleteLastIntakeUseCaseImpl(
    private val repository: WaterIntakeRepository
) : DeleteLastIntakeUseCase {
    override suspend fun invoke(): Result<Unit> {
        return repository.deleteLastIntake()
    }
}
