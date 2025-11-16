package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.WaterIntakeRepository

class GetTodayTotalUseCase(
    private val repository: WaterIntakeRepository
) {
    suspend operator fun invoke(): Int {
        return repository.getTodayTotalMl()
    }
}