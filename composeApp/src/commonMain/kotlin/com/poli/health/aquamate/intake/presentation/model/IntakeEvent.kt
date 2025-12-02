package com.poli.health.aquamate.intake.presentation.model

import com.poli.health.aquamate.intake.domain.model.PresetVolume
import kotlinx.datetime.LocalDate

sealed class IntakeEvent {
    data class RegisterPreset(val preset: PresetVolume) : IntakeEvent()
    data class RegisterCustom(val volumeMl: Int) : IntakeEvent()
    data class UpdateCustomVolume(val volumeMl: Int) : IntakeEvent()
    data object IncrementVolume : IntakeEvent()
    data object DecrementVolume : IntakeEvent()
    data class SelectDate(val date: LocalDate) : IntakeEvent()
    data class DeleteIntake(val intakeId: String) : IntakeEvent()
    data object DeleteLastIntake : IntakeEvent()
    data object LoadWeeklyStats : IntakeEvent()
    data object ClearError : IntakeEvent()
    data object ClearSuccess : IntakeEvent()
}
