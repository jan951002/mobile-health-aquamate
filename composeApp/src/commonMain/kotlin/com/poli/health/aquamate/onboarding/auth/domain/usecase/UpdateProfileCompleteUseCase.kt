package com.poli.health.aquamate.onboarding.auth.domain.usecase

interface UpdateProfileCompleteUseCase {
    suspend operator fun invoke(isComplete: Boolean)
}
