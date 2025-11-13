package com.poli.health.aquamate.onboarding.auth.data.datasource

import com.poli.health.aquamate.onboarding.auth.data.model.AuthUser
import com.poli.health.aquamate.onboarding.auth.data.model.LoginResult
import kotlinx.coroutines.flow.Flow

internal interface AuthRemoteDataSource {

    val authState: Flow<LoginResult?>

    suspend fun signInWithEmail(email: String, password: String): LoginResult

    suspend fun signOut()

    suspend fun getCurrentUser(): AuthUser?
}
