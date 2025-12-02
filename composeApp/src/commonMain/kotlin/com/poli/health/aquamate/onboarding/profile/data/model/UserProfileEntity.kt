package com.poli.health.aquamate.onboarding.profile.data.model

import kotlinx.serialization.Serializable

/**
 * Firestore entity for UserProfile
 * birthDateMillis: Date of birth stored as milliseconds since epoch (UTC)
 */
@Serializable
data class UserProfileEntity(
    val userId: String = "",
    val weightKg: Double = 0.0,
    val heightCm: Double = 0.0,
    val birthDateMillis: Long = 0L,
    val gender: String = "",
    val activityLevel: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)
