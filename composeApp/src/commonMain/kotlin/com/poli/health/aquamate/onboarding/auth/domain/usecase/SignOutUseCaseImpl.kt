package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.LoginRepository

internal class SignOutUseCaseImpl(
    private val loginRepository: LoginRepository
) : SignOutUseCase {

    override suspend fun invoke() {
        loginRepository.signOut()
    }
}
