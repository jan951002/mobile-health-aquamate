package com.poli.health.aquamate.intake.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.usecase.GetUserProfileUseCase
import kotlinx.coroutines.flow.first

internal class GetUserDailyGoalUseCaseImpl(
    private val getUserProfileUseCase: GetUserProfileUseCase
) : GetUserDailyGoalUseCase {

    override suspend fun invoke(userId: String): Int {
        val profile = getUserProfileUseCase(userId).first()
        return profile?.dailyWaterGoalMl ?: DEFAULT_DAILY_GOAL_ML
    }

    companion object {
        private const val DEFAULT_DAILY_GOAL_ML = 2000
    }
}
