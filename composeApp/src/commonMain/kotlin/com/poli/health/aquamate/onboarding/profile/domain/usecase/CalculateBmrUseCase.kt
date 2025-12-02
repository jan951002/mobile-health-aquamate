package com.poli.health.aquamate.onboarding.profile.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.model.Gender

interface CalculateBmrUseCase {
    operator fun invoke(weightKg: Double, age: Int, gender: Gender): Double
}
