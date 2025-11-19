package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.AuthRepository
import com.poli.health.aquamate.onboarding.auth.domain.mapper.AuthStateMapper
import com.poli.health.aquamate.onboarding.auth.domain.model.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ObserveAuthStateUseCaseImpl(
    private val authRepository: AuthRepository,
    private val authStateMapper: AuthStateMapper
) : ObserveAuthStateUseCase {

    override fun invoke(): Flow<AuthState> = authRepository
        .loginState
        .map { loginResult -> authStateMapper.toAuthState(loginResult) }
}
