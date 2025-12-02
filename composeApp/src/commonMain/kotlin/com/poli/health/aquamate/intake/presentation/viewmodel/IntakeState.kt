package com.poli.health.aquamate.intake.presentation.viewmodel

import com.poli.health.aquamate.intake.domain.model.DailyIntake
import com.poli.health.aquamate.intake.domain.model.WeeklyStats
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
data class IntakeState(
    val customVolume: Int = 350,
    val selectedDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val dailyIntake: DailyIntake? = null,
    val dailyGoalMl: Int = 0,
    val progressPercentage: Int = 0,
    val weeklyStats: WeeklyStats? = null,
    val isLoading: Boolean = false,
    val isLoadingStats: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)
