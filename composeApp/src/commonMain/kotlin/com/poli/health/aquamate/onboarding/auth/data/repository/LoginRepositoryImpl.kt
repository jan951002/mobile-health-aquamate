package com.poli.health.aquamate.onboarding.auth.data.repository

import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthRemoteDataSource
import com.poli.health.aquamate.onboarding.auth.data.model.LoginResult
import kotlinx.coroutines.flow.Flow

internal class LoginRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource
) : LoginRepository {

    override val loginState: Flow<LoginResult?> = authRemoteDataSource.authState

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): LoginResult = authRemoteDataSource.signInWithEmail(email, password)

    override suspend fun signUpWithEmail(
        email: String,
        password: String
    ): LoginResult = authRemoteDataSource.signUpWithEmail(email, password)

    override suspend fun signOut() {
        authRemoteDataSource.signOut()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return authRemoteDataSource.getCurrentUser() != null
    }
}
