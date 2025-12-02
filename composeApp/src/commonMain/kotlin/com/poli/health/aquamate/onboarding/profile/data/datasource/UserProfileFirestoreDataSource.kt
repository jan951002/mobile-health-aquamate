package com.poli.health.aquamate.onboarding.profile.data.datasource

import com.poli.health.aquamate.onboarding.profile.data.error.FirestoreErrorHandler
import com.poli.health.aquamate.onboarding.profile.data.model.UserProfileEntity
import com.poli.health.aquamate.onboarding.profile.domain.exception.ProfileException
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.serializer

internal class UserProfileFirestoreDataSource(
    private val firestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher,
    private val errorHandler: FirestoreErrorHandler
) : UserProfileRemoteDataSource {

    companion object {
        private const val COLLECTION_USER_PROFILES = "user_profiles"
    }

    override suspend fun saveUserProfile(
        profile: UserProfileEntity
    ): Result<Unit> = withContext(ioDispatcher) {
        try {
            firestore.collection(COLLECTION_USER_PROFILES)
                .document(profile.userId)
                .set(profile)
            Result.success(Unit)
        } catch (e: Exception) {
            val profileError = errorHandler.mapError(e)
            Result.failure(ProfileException(profileError, e))
        }
    }

    override suspend fun getUserProfile(
        userId: String
    ): Result<UserProfileEntity?> = withContext(ioDispatcher) {
        try {
            val document = firestore.collection(COLLECTION_USER_PROFILES)
                .document(userId)
                .get()

            val profile = if (document.exists) {
                document.data(serializer<UserProfileEntity>())
            } else {
                null
            }
            Result.success(profile)
        } catch (e: Exception) {
            val profileError = errorHandler.mapError(e)
            Result.failure(ProfileException(profileError, e))
        }
    }
}
