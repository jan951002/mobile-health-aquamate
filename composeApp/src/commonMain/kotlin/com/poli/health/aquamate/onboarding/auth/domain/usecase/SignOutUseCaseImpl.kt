package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.AuthRepository

internal class SignOutUseCaseImpl(
    private val authRepository: AuthRepository
) : SignOutUseCase {

    override suspend fun invoke() {
        authRepository.signOut()
    }
}
