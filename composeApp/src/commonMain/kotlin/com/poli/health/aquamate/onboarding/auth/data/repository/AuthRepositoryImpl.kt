package com.poli.health.aquamate.onboarding.auth.data.repository

import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthLocalDataSource
import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthRemoteDataSource
import com.poli.health.aquamate.onboarding.auth.data.model.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {

    override val loginState: Flow<LoginResult?> = authRemoteDataSource.authState

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): LoginResult {
        val result = authRemoteDataSource.signInWithEmail(email, password)
        if (result is LoginResult.Success) {
            authLocalDataSource.saveSession(result.user.uid, result.user.email)
        }
        return result
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String
    ): LoginResult {
        val result = authRemoteDataSource.signUpWithEmail(email, password)
        if (result is LoginResult.Success) {
            authLocalDataSource.saveSession(result.user.uid, result.user.email)
        }
        return result
    }

    override suspend fun signOut() {
        authRemoteDataSource.signOut()
        authLocalDataSource.clearSession()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return authRemoteDataSource.getCurrentUser() != null
    }

    override fun getCurrentUserId(): Flow<String?> {
        return authLocalDataSource.getSession().map { it?.userId }
    }
}
