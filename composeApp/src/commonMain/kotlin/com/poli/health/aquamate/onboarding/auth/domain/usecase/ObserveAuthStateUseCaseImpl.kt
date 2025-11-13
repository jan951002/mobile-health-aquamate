package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.LoginRepository
import com.poli.health.aquamate.onboarding.auth.domain.mapper.AuthStateMapper
import com.poli.health.aquamate.onboarding.auth.domain.model.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ObserveAuthStateUseCaseImpl(
    private val loginRepository: LoginRepository,
    private val authStateMapper: AuthStateMapper
) : ObserveAuthStateUseCase {

    override fun invoke(): Flow<AuthState> = loginRepository
        .loginState
        .map { loginResult -> authStateMapper.toAuthState(loginResult) }
}
