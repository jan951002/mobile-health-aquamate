package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.WaterIntakeRepository

internal class GetTodayTotalUseCaseImpl(
    private val repository: WaterIntakeRepository
) : GetTodayTotalUseCase {
    override suspend fun invoke(): Int {
        return repository.getTodayTotalMl()
    }
}
