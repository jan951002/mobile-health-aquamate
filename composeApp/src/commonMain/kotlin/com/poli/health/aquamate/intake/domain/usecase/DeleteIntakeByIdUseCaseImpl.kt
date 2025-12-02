package com.poli.health.aquamate.intake.domain.usecase

import com.poli.health.aquamate.intake.data.repository.WaterIntakeRepository
import kotlinx.datetime.LocalDate

internal class DeleteIntakeByIdUseCaseImpl(
    private val repository: WaterIntakeRepository
) : DeleteIntakeByIdUseCase {

    override suspend fun invoke(userId: String, date: LocalDate, intakeId: String): Result<Unit> {
        return repository.deleteIntakeById(userId, date, intakeId)
    }
}
