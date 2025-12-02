package com.poli.health.aquamate.onboarding.profile.domain.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.datetime.until

/**
 * Domain model representing a user's health profile.
 * Uses value objects for measurements to support international units.
 */
@OptIn(ExperimentalTime::class)
data class UserProfile(
    val userId: String,
    val weight: Weight,
    val height: Height,
    val birthDate: LocalDate,
    val gender: Gender,
    val activityLevel: ActivityLevel,
    val dailyWaterGoalMl: Int = 0,
    val bmi: Double = 0.0,
    val bmr: Double = 0.0,
    val totalEnergyExpenditure: Double = 0.0,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
) {
    /**
     * Convenience properties for backward compatibility and calculations
     */
    val weightKg: Double get() = weight.inKilograms
    val heightCm: Double get() = height.inCentimeters

    /**
     * Calculate current age dynamically from birthDate
     */
    val age: Int get() {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        return birthDate.until(today, DateTimeUnit.YEAR).toInt()
    }
}
