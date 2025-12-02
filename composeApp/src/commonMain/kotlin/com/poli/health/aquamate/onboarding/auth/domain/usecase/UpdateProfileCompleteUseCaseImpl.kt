package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthLocalDataSource

internal class UpdateProfileCompleteUseCaseImpl(
    private val authLocalDataSource: AuthLocalDataSource
) : UpdateProfileCompleteUseCase {

    override suspend fun invoke(isComplete: Boolean) {
        authLocalDataSource.updateProfileComplete(isComplete)
    }
}
