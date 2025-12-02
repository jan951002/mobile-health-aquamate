package com.poli.health.aquamate.intake.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyIntakeEntity(
    val date: String = "",
    val intakes: List<IntakeRecordEntity> = emptyList(),
    val totalMl: Int = 0,
    val goalMl: Int = 0,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)
