package com.poli.health.aquamate.onboarding.auth.domain.usecase

interface DeleteLastIntakeUseCase {
    suspend operator fun invoke(): Result<Unit>
}
