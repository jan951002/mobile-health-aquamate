package com.poli.health.aquamate.onboarding.auth.data.dao

import com.poli.health.aquamate.onboarding.auth.data.model.UserSessionEntity
import kotlinx.coroutines.flow.Flow

internal interface SessionDao {

    suspend fun insertSession(session: UserSessionEntity)

    fun getSession(): Flow<UserSessionEntity?>

    suspend fun updateProfileComplete(isComplete: Boolean)

    suspend fun deleteSession()
}
