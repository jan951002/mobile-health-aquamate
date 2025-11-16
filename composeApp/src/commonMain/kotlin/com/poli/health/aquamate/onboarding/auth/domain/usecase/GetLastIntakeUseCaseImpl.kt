package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.WaterIntakeRepository
import com.poli.health.aquamate.onboarding.auth.domain.model.WaterIntake

internal class GetLastIntakeUseCaseImpl(
    private val repository: WaterIntakeRepository
) : GetLastIntakeUseCase {
    override suspend fun invoke(): WaterIntake? {
        return repository.getLastIntake()
    }
}
