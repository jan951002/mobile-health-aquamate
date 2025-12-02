package com.poli.health.aquamate.onboarding.profile.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.exception.ProfileException
import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileError
import com.poli.health.aquamate.onboarding.profile.domain.repository.UserProfileRepository

internal class HasUserProfileUseCaseImpl(
    private val repository: UserProfileRepository
) : HasUserProfileUseCase {

    override suspend fun invoke(userId: String): Boolean {
        if (userId.isBlank()) {
            throw ProfileException(ProfileError.Validation.UserIdBlank)
        }
        return repository.hasUserProfile(userId)
    }
}
