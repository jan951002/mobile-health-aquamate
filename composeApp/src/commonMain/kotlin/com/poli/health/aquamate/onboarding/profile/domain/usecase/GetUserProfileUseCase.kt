package com.poli.health.aquamate.onboarding.profile.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface GetUserProfileUseCase {
    operator fun invoke(userId: String): Flow<UserProfile?>
}
