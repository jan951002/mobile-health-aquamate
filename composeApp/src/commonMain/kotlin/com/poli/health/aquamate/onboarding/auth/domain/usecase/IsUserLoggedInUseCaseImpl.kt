package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.LoginRepository

internal class IsUserLoggedInUseCaseImpl(
    private val loginRepository: LoginRepository
) : IsUserLoggedInUseCase {

    override suspend fun invoke(): Boolean = loginRepository.isUserLoggedIn()
}
