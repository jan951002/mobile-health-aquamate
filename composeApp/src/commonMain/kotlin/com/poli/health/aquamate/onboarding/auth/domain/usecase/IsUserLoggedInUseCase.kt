package com.poli.health.aquamate.onboarding.auth.domain.usecase

internal interface IsUserLoggedInUseCase {

    suspend operator fun invoke(): Boolean
}
