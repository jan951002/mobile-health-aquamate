package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.AuthRepository

internal class IsUserLoggedInUseCaseImpl(
    private val authRepository: AuthRepository
) : IsUserLoggedInUseCase {

    override suspend fun invoke(): Boolean = authRepository.isUserLoggedIn()
}
