package com.poli.health.aquamate.onboarding.profile.data.dao

import com.poli.health.aquamate.onboarding.profile.data.model.UserProfileLocalEntity
import kotlinx.coroutines.flow.Flow

internal interface UserProfileDao {
    suspend fun saveProfile(profile: UserProfileLocalEntity)
    fun getProfile(): Flow<UserProfileLocalEntity?>
    suspend fun deleteProfile()
}
