package com.poli.health.aquamate.onboarding.profile.data.datasource

import com.poli.health.aquamate.onboarding.profile.data.dao.UserProfileDao
import com.poli.health.aquamate.onboarding.profile.data.model.UserProfileLocalEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

internal class UserProfileLocalDataSourceImpl(
    private val userProfileDao: UserProfileDao,
    private val ioDispatcher: CoroutineDispatcher
) : UserProfileLocalDataSource {

    override suspend fun saveProfile(profile: UserProfileLocalEntity) {
        withContext(ioDispatcher) {
            userProfileDao.saveProfile(profile)
        }
    }

    override fun getProfile(): Flow<UserProfileLocalEntity?> {
        return userProfileDao.getProfile().flowOn(ioDispatcher)
    }

    override suspend fun deleteProfile() {
        withContext(ioDispatcher) {
            userProfileDao.deleteProfile()
        }
    }
}
