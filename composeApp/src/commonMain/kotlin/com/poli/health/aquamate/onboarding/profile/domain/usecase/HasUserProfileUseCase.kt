package com.poli.health.aquamate.onboarding.profile.domain.usecase

interface HasUserProfileUseCase {
    suspend operator fun invoke(userId: String): Boolean
}
