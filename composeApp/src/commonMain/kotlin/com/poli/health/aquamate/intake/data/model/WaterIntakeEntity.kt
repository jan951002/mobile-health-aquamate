package com.poli.health.aquamate.intake.data.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal data class WaterIntakeEntity(
    val id: String,
    val volumeMl: Int,
    val timestampMillis: Long
) {
    companion object {
        fun create(volumeMl: Int): WaterIntakeEntity {
            val now = Clock.System.now()
            return WaterIntakeEntity(
                id = "${now.toEpochMilliseconds()}_${volumeMl}",
                volumeMl = volumeMl,
                timestampMillis = now.toEpochMilliseconds()
            )
        }
    }
}
