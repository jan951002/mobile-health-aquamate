package com.poli.health.aquamate.intake.domain.model

import kotlinx.datetime.LocalDate

data class DailyIntake(
    val date: LocalDate,
    val intakes: List<WaterIntake>,
    val totalMl: Int,
    val goalMl: Int
)
