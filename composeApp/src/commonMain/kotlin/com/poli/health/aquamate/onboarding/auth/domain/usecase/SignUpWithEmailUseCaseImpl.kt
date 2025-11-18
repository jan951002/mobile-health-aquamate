package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.LoginRepository
import com.poli.health.aquamate.onboarding.auth.domain.mapper.AuthStateMapper
import com.poli.health.aquamate.onboarding.auth.domain.model.AuthState

internal class SignUpWithEmailUseCaseImpl(
    private val loginRepository: LoginRepository,
    private val authStateMapper: AuthStateMapper
) : SignUpWithEmailUseCase {

    override suspend fun invoke(email: String, password: String): AuthState {
        val loginResult = loginRepository.signUpWithEmail(email, password)
        return authStateMapper.toAuthState(loginResult)
    }
}
