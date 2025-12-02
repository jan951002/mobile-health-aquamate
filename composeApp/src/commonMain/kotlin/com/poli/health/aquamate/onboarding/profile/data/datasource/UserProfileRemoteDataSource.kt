package com.poli.health.aquamate.onboarding.profile.data.datasource

import com.poli.health.aquamate.onboarding.profile.data.model.UserProfileEntity

internal interface UserProfileRemoteDataSource {
    suspend fun saveUserProfile(profile: UserProfileEntity): Result<Unit>
    suspend fun getUserProfile(userId: String): Result<UserProfileEntity?>
}
