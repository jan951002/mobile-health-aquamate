package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.domain.model.WaterIntake
import com.poli.health.aquamate.onboarding.auth.data.repository.WaterIntakeRepository

class GetLastIntakeUseCase(
    private val repository: WaterIntakeRepository
) {
    suspend operator fun invoke(): WaterIntake? {
        return repository.getLastIntake()
    }
}