package com.poli.health.aquamate.onboarding.auth.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetCurrentUserIdUseCase {

    operator fun invoke(): Flow<String?>
}
