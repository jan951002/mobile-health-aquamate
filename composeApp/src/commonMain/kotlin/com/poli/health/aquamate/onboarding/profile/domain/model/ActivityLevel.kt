package com.poli.health.aquamate.onboarding.profile.domain.model

enum class ActivityLevel(
    val waterFactorMlPerKg: Int,
    val energyFactor: Double
) {
    SEDENTARY(
        waterFactorMlPerKg = 30,
        energyFactor = 1.40
    ),
    MODERATE(
        waterFactorMlPerKg = 35,
        energyFactor = 1.75
    ),
    INTENSE(
        waterFactorMlPerKg = 40,
        energyFactor = 2.40
    )
}
