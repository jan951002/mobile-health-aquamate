package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.data.repository.LoginRepository
import com.poli.health.aquamate.onboarding.auth.domain.mapper.AuthStateMapper
import com.poli.health.aquamate.onboarding.auth.domain.model.AuthState

internal class SignInWithEmailUseCaseImpl(
    private val loginRepository: LoginRepository,
    private val authStateMapper: AuthStateMapper
) : SignInWithEmailUseCase {

    override suspend fun invoke(email: String, password: String): AuthState {
        val loginResult = loginRepository.signInWithEmail(email, password)
        return authStateMapper.toAuthState(loginResult)
    }
}
