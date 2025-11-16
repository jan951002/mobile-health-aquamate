package com.poli.health.aquamate.onboarding.auth.domain.usecase

interface GetTodayTotalUseCase {
    suspend operator fun invoke(): Int
}
