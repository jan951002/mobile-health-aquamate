package com.poli.health.aquamate.onboarding.profile.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.model.UserProfile

interface SaveUserProfileUseCase {
    suspend operator fun invoke(profile: UserProfile): Result<Unit>
}
