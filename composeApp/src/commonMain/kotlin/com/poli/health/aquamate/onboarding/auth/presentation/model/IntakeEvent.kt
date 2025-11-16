package com.poli.health.aquamate.onboarding.auth.presentation.model

import com.poli.health.aquamate.onboarding.auth.domain.model.PresetVolume

sealed class IntakeEvent {
    data class RegisterPreset(val preset: PresetVolume) : IntakeEvent()
    data class RegisterCustom(val volumeMl: Int) : IntakeEvent()
    data class UpdateCustomVolume(val volumeMl: Int) : IntakeEvent()
    data object DeleteLastIntake : IntakeEvent()
    data object ClearError : IntakeEvent()
    data object ClearSuccess : IntakeEvent()
}
