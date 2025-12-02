package com.poli.health.aquamate.intake.domain.usecase

import com.poli.health.aquamate.intake.domain.model.WeeklyStats

interface GetWeeklyStatsUseCase {
    suspend operator fun invoke(userId: String): WeeklyStats
}
