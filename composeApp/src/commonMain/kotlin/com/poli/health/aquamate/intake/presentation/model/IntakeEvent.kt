package com.poli.health.aquamate.intake.presentation.model

import com.poli.health.aquamate.intake.domain.model.PresetVolume

sealed class IntakeEvent {
    data class RegisterPreset(val preset: PresetVolume) : IntakeEvent()
    data class RegisterCustom(val volumeMl: Int) : IntakeEvent()
    data class UpdateCustomVolume(val volumeMl: Int) : IntakeEvent()
    data object DeleteLastIntake : IntakeEvent()
    data object ClearError : IntakeEvent()
    data object ClearSuccess : IntakeEvent()
}
