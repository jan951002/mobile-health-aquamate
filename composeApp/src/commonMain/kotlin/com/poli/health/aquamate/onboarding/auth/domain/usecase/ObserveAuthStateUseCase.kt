package com.poli.health.aquamate.onboarding.auth.domain.usecase

import com.poli.health.aquamate.onboarding.auth.domain.model.AuthState
import kotlinx.coroutines.flow.Flow

interface ObserveAuthStateUseCase {

    operator fun invoke(): Flow<AuthState>
}
