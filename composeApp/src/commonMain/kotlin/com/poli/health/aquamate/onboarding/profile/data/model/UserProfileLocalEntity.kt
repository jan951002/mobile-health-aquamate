package com.poli.health.aquamate.onboarding.profile.data.model

/**
 * Local storage entity for UserProfile
 * birthDateMillis: Date of birth stored as milliseconds since epoch (UTC)
 */
internal data class UserProfileLocalEntity(
    val userId: String,
    val weightKg: Double,
    val heightCm: Double,
    val birthDateMillis: Long,
    val gender: String,
    val activityLevel: String,
    val dailyWaterGoalMl: Int,
    val bmi: Double,
    val bmr: Double,
    val totalEnergyExpenditure: Double,
    val createdAt: Long,
    val updatedAt: Long,
    val lastSyncedAt: Long
)
