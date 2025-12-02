package com.poli.health.aquamate.intake.presentation.viewmodel

import com.poli.health.aquamate.intake.domain.model.WaterIntake

data class IntakeState(
    val customVolume: Int = 350,
    val lastIntake: WaterIntake? = null,
    val todayTotal: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)
