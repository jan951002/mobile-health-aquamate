package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.WaterIntakeRepository

class DeleteLastIntakeUseCase(
    private val repository: WaterIntakeRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.deleteLastIntake()
    }
}