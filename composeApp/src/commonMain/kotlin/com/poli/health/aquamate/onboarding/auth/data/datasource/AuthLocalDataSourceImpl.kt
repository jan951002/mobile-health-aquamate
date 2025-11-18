package com.poli.health.aquamate.onboarding.auth.data.datasource

import com.poli.health.aquamate.onboarding.auth.data.dao.SessionDao
import com.poli.health.aquamate.onboarding.auth.data.model.UserSessionEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

internal class AuthLocalDataSourceImpl(
    private val sessionDao: SessionDao,
    private val ioDispatcher: CoroutineDispatcher
) : AuthLocalDataSource {

    override suspend fun saveSession(userId: String, email: String?) {
        withContext(ioDispatcher) {
            sessionDao.insertSession(
                UserSessionEntity(
                    userId = userId,
                    email = email,
                    isProfileComplete = false
                )
            )
        }
    }

    override fun getSession(): Flow<UserSessionEntity?> {
        return sessionDao.getSession().flowOn(ioDispatcher)
    }

    override suspend fun updateProfileComplete(isComplete: Boolean) {
        withContext(ioDispatcher) {
            sessionDao.updateProfileComplete(isComplete)
        }
    }

    override suspend fun clearSession() {
        withContext(ioDispatcher) {
            sessionDao.deleteSession()
        }
    }
}
