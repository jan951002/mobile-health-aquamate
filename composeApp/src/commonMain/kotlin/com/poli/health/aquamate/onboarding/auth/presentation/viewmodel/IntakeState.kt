package com.poli.health.aquamate.onboarding.auth.presentation.viewmodel

import com.poli.health.aquamate.onboarding.auth.domain.model.PresetVolume
import com.poli.health.aquamate.onboarding.auth.domain.model.WaterIntake

data class IntakeState(
    val customVolume: Int = 350,
    val lastIntake: WaterIntake? = null,
    val todayTotal: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)

sealed class IntakeEvent {
    data class RegisterPreset(val preset: PresetVolume) : IntakeEvent()
    data class RegisterCustom(val volumeMl: Int) : IntakeEvent()
    data class UpdateCustomVolume(val volumeMl: Int) : IntakeEvent()
    object DeleteLastIntake : IntakeEvent()
    object ClearError : IntakeEvent()
    object ClearSuccess : IntakeEvent()
}