package com.poli.health.aquamate.intake.domain.usecase

import com.poli.health.aquamate.intake.data.repository.WaterIntakeRepository

internal class GetTodayTotalUseCaseImpl(
    private val repository: WaterIntakeRepository
) : GetTodayTotalUseCase {
    override suspend fun invoke(): Int {
        return repository.getTodayTotalMl()
    }
}
