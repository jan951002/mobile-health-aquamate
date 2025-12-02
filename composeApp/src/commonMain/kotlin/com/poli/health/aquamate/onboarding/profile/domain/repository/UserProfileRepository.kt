package com.poli.health.aquamate.onboarding.profile.domain.repository

import com.poli.health.aquamate.onboarding.profile.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    suspend fun saveUserProfile(profile: UserProfile): Result<Unit>
    fun getUserProfile(userId: String): Flow<UserProfile?>
    suspend fun hasUserProfile(userId: String): Boolean
    suspend fun syncFromRemote(userId: String): Result<Unit>
    suspend fun deleteUserProfile()
}
