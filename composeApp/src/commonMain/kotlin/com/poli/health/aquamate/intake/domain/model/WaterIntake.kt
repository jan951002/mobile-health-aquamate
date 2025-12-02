package com.poli.health.aquamate.intake.domain.model

import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

data class WaterIntake @OptIn(ExperimentalTime::class) constructor(
    val id: String,
    val volumeMl: Int,
    val timestamp: Instant,
    val date: LocalDateTime = timestamp.toLocalDateTime(TimeZone.currentSystemDefault())
) {
    companion object {
        @OptIn(ExperimentalTime::class)
        fun create(volumeMl: Int): WaterIntake {
            val now = Clock.System.now()
            return WaterIntake(
                id = "${now.toEpochMilliseconds()}_${volumeMl}",
                volumeMl = volumeMl,
                timestamp = now
            )
        }
    }
}
