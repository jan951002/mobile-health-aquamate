package com.poli.health.aquamate.onboarding.profile.domain.usecase

interface CalculateBmiUseCase {
    operator fun invoke(weightKg: Double, heightCm: Double): Double
}
