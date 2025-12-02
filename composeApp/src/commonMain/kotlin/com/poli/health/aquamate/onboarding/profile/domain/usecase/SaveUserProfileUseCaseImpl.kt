package com.poli.health.aquamate.onboarding.profile.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileValidationResult
import com.poli.health.aquamate.onboarding.profile.domain.model.UserProfile
import com.poli.health.aquamate.onboarding.profile.domain.repository.UserProfileRepository
import com.poli.health.aquamate.onboarding.profile.domain.validator.UserProfileValidator

internal class SaveUserProfileUseCaseImpl(
    private val repository: UserProfileRepository,
    private val validator: UserProfileValidator
) : SaveUserProfileUseCase {

    override suspend fun invoke(profile: UserProfile): Result<Unit> {
        val validationResult = validator.validate(profile)

        return when (validationResult) {
            is ProfileValidationResult.Valid -> {
                repository.saveUserProfile(profile)
            }
            is ProfileValidationResult.Invalid -> {
                val errorMessage = validationResult.errors.values.joinToString(", ")
                Result.failure(IllegalArgumentException(errorMessage))
            }
        }
    }
}
