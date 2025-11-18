package com.poli.health.aquamate.onboarding.auth.data.datasource

import com.poli.health.aquamate.onboarding.auth.data.model.UserSessionEntity
import kotlinx.coroutines.flow.Flow

internal interface AuthLocalDataSource {

    suspend fun saveSession(userId: String, email: String?)

    fun getSession(): Flow<UserSessionEntity?>

    suspend fun updateProfileComplete(isComplete: Boolean)

    suspend fun clearSession()
}
