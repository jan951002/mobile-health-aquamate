package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.domain.model.AuthState

interface SignUpWithEmailUseCase {
    suspend operator fun invoke(email: String, password: String): AuthState
}
