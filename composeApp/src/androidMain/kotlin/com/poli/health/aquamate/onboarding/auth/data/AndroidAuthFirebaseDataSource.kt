package com.poli.health.aquamate.onboarding.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthRemoteDataSource
import com.poli.health.aquamate.onboarding.auth.data.model.AuthUser
import com.poli.health.aquamate.onboarding.auth.data.model.LoginResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

internal class AndroidAuthFirebaseDataSource(
    private val firebaseAuth: FirebaseAuth,
    private val ioDispatcher: CoroutineDispatcher,
    private val errorHandler: FirebaseAuthErrorHandler
) : AuthRemoteDataSource {

    override val authState: Flow<LoginResult?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val result = auth.currentUser?.toAuthUser()?.let { authUser ->
                LoginResult.Success(user = authUser)
            }
            trySend(result)
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): LoginResult = withContext(ioDispatcher) {
        try {
            val authResult = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            val firebaseUser = authResult.user
                ?: return@withContext LoginResult.Error(
                    message = "User not found after login"
                )

            LoginResult.Success(user = firebaseUser.toAuthUser())
        } catch (e: FirebaseAuthException) {
            LoginResult.Error(
                message = errorHandler.getErrorMessage(e),
                cause = e
            )
        } catch (e: Exception) {
            LoginResult.Error(
                message = "Unexpected error: ${e.message}",
                cause = e
            )
        }
    }

    override suspend fun signOut() = withContext(ioDispatcher) {
        firebaseAuth.signOut()
    }

    override suspend fun getCurrentUser(): AuthUser? = withContext(ioDispatcher) {
        firebaseAuth.currentUser?.toAuthUser()
    }

    private fun FirebaseUser.toAuthUser(): AuthUser = AuthUser(
        uid = this.uid,
        email = this.email
    )
}
