package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.model.LoginResult
import com.poli.health.aquamate.onboarding.auth.data.repository.LoginRepository
import com.poli.health.aquamate.onboarding.auth.domain.mapper.AuthStateMapper
import com.poli.health.aquamate.onboarding.auth.domain.model.AuthState

internal class SignUpWithEmailUseCaseImpl(
    private val loginRepository: LoginRepository,
    private val authStateMapper: AuthStateMapper
) : SignUpWithEmailUseCase {

    override suspend fun invoke(email: String, password: String): AuthState {
        return when (val result = loginRepository.signUpWithEmail(email, password)) {
            is LoginResult.Loading -> AuthState.Loading
            is LoginResult.Success -> authStateMapper.toAuthState(result)
            is LoginResult.Error -> authStateMapper.toAuthState(result)
        }
    }
}
