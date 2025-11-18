package com.poli.health.aquamate.onboarding.auth.data.model

internal data class UserSessionEntity(
    val userId: String,
    val email: String?,
    val isProfileComplete: Boolean = false
)
