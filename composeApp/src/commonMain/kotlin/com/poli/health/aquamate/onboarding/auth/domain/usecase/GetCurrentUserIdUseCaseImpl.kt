package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

internal class GetCurrentUserIdUseCaseImpl(
    private val authRepository: AuthRepository
) : GetCurrentUserIdUseCase {

    override fun invoke(): Flow<String?> = authRepository.getCurrentUserId()
}
