package com.poli.health.aquamate.intake.domain.usecase

interface GetUserDailyGoalUseCase {
    suspend operator fun invoke(userId: String): Int
}
