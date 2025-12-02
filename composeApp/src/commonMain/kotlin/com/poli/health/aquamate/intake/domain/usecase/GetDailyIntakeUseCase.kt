package com.poli.health.aquamate.intake.domain.usecase

import com.poli.health.aquamate.intake.domain.model.DailyIntake
import kotlinx.datetime.LocalDate

interface GetDailyIntakeUseCase {
    suspend operator fun invoke(userId: String, date: LocalDate): DailyIntake?
}
