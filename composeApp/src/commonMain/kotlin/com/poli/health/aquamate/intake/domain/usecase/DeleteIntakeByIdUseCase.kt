package com.poli.health.aquamate.intake.domain.usecase

import kotlinx.datetime.LocalDate

interface DeleteIntakeByIdUseCase {
    suspend operator fun invoke(userId: String, date: LocalDate, intakeId: String): Result<Unit>
}
