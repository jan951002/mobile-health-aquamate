package com.poli.health.aquamate.onboarding.profile.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.exception.ProfileException
import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileError
import com.poli.health.aquamate.onboarding.profile.domain.model.UserProfile
import com.poli.health.aquamate.onboarding.profile.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow

internal class GetUserProfileUseCaseImpl(
    private val repository: UserProfileRepository
) : GetUserProfileUseCase {

    override fun invoke(userId: String): Flow<UserProfile?> {
        if (userId.isBlank()) {
            throw ProfileException(ProfileError.Validation.UserIdBlank)
        }
        return repository.getUserProfile(userId)
    }
}
