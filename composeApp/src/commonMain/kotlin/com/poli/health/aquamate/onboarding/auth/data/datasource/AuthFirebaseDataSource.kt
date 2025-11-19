package com.poli.health.aquamate.onboarding.auth.data.datasource

import com.poli.health.aquamate.onboarding.auth.data.error.FirebaseAuthErrorHandler
import com.poli.health.aquamate.onboarding.auth.data.model.AuthUser
import com.poli.health.aquamate.onboarding.auth.data.model.LoginResult
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class AuthFirebaseDataSource(
    private val firebaseAuth: FirebaseAuth,
    private val ioDispatcher: CoroutineDispatcher,
    private val errorHandler: FirebaseAuthErrorHandler
) : AuthRemoteDataSource {

    override val authState: Flow<LoginResult?> = firebaseAuth.authStateChanged.map { user ->
        user?.toAuthUser()?.let { LoginResult.Success(user = it) }
    }

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): LoginResult = withContext(ioDispatcher) {
        try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password)
            val user = authResult.user?.toAuthUser()
            
            if (user != null) {
                LoginResult.Success(user = user)
            } else {
                LoginResult.Error(message = "User not found after login")
            }
        } catch (e: Exception) {
            LoginResult.Error(
                message = errorHandler.getErrorMessage(e),
                cause = e
            )
        }
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String
    ): LoginResult = withContext(ioDispatcher) {
        try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password)
            val user = authResult.user?.toAuthUser()
            
            if (user != null) {
                LoginResult.Success(user = user)
            } else {
                LoginResult.Error(message = "User not created")
            }
        } catch (e: Exception) {
            LoginResult.Error(
                message = errorHandler.getErrorMessage(e),
                cause = e
            )
        }
    }

    override suspend fun signOut(): Unit = withContext(ioDispatcher) {
        try {
            firebaseAuth.signOut()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCurrentUser(): AuthUser? = withContext(ioDispatcher) {
        firebaseAuth.currentUser?.toAuthUser()
    }

    private fun FirebaseUser.toAuthUser(): AuthUser? {
        val userId = this.uid
        if (userId.isEmpty()) return null

        return AuthUser(
            uid = userId,
            email = this.email
        )
    }
}
