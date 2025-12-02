package com.poli.health.aquamate.intake.data.model

import kotlinx.serialization.Serializable

@Serializable
data class IntakeRecordEntity(
    val id: String = "",
    val volumeMl: Int = 0,
    val timestampMillis: Long = 0L
)
