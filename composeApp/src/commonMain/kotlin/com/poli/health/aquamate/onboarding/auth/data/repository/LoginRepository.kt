package com.poli.health.aquamate.onboarding.auth.data.repository

import com.poli.health.aquamate.onboarding.auth.data.model.LoginResult
import kotlinx.coroutines.flow.Flow

internal interface LoginRepository {

    val loginState: Flow<LoginResult?>

    suspend fun signInWithEmail(email: String, password: String): LoginResult

    suspend fun signUpWithEmail(email: String, password: String): LoginResult

    suspend fun signOut()

    suspend fun isUserLoggedIn(): Boolean
}
