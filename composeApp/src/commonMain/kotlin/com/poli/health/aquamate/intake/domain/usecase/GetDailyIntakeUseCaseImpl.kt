package com.poli.health.aquamate.intake.domain.usecase

import com.poli.health.aquamate.intake.data.repository.WaterIntakeRepository
import com.poli.health.aquamate.intake.domain.model.DailyIntake
import kotlinx.datetime.LocalDate

internal class GetDailyIntakeUseCaseImpl(
    private val repository: WaterIntakeRepository
) : GetDailyIntakeUseCase {

    override suspend fun invoke(userId: String, date: LocalDate): DailyIntake? {
        return repository.getDailyIntake(userId, date)
    }
}
