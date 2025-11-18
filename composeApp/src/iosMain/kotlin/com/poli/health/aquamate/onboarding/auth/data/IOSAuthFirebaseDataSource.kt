package com.poli.health.aquamate.onboarding.auth.data

import cocoapods.FirebaseAuth.FIRAuth
import cocoapods.FirebaseAuth.FIRAuthDataResult
import cocoapods.FirebaseAuth.FIRUser
import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthRemoteDataSource
import com.poli.health.aquamate.onboarding.auth.data.model.AuthUser
import com.poli.health.aquamate.onboarding.auth.data.model.LoginResult
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume

@OptIn(ExperimentalForeignApi::class)
internal class IOSAuthFirebaseDataSource(
    private val firebaseAuth: FIRAuth,
    private val ioDispatcher: CoroutineDispatcher,
    private val errorHandler: FirebaseAuthErrorHandler
) : AuthRemoteDataSource {

    override val authState: Flow<LoginResult?> = callbackFlow {
        val handle = firebaseAuth.addAuthStateDidChangeListener { _, user ->
            val result = user?.toAuthUser()?.let { authUser ->
                LoginResult.Success(user = authUser)
            }
            trySend(result)
        }
        awaitClose {
            firebaseAuth.removeAuthStateDidChangeListener(handle)
        }
    }

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): LoginResult = withContext(ioDispatcher) {
        suspendCancellableCoroutine { continuation ->
            firebaseAuth.signInWithEmail(
                email = email,
                password = password,
                completion = { authResult: FIRAuthDataResult?, error: NSError? ->
                    when {
                        error != null -> {
                            continuation.resume(
                                LoginResult.Error(
                                    message = errorHandler.getErrorMessage(error),
                                    cause = Exception(error.localizedDescription ?: "Unknown error")
                                )
                            )
                        }
                        authResult?.user() == null -> {
                            continuation.resume(
                                LoginResult.Error(
                                    message = "User not found after login"
                                )
                            )
                        }
                        else -> {
                            continuation.resume(
                                LoginResult.Success(
                                    user = authResult.user()!!.toAuthUser()
                                )
                            )
                        }
                    }
                }
            )
        }
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String
    ): LoginResult = withContext(ioDispatcher) {
        suspendCancellableCoroutine { continuation ->
            firebaseAuth.createUserWithEmail(
                email = email,
                password = password,
                completion = { authResult: FIRAuthDataResult?, error: NSError? ->
                    when {
                        error != null -> {
                            continuation.resume(
                                LoginResult.Error(
                                    message = errorHandler.getErrorMessage(error),
                                    cause = Exception(error.localizedDescription ?: "Unknown error")
                                )
                            )
                        }
                        authResult?.user() == null -> {
                            continuation.resume(
                                LoginResult.Error(
                                    message = "User not created"
                                )
                            )
                        }
                        else -> {
                            continuation.resume(
                                LoginResult.Success(
                                    user = authResult.user()!!.toAuthUser()
                                )
                            )
                        }
                    }
                }
            )
        }
    }

    override suspend fun signOut(): Unit = withContext(ioDispatcher) {
        val errorPtr = null
        firebaseAuth.signOut(errorPtr)
        Unit
    }

    override suspend fun getCurrentUser(): AuthUser? = withContext(ioDispatcher) {
        firebaseAuth.currentUser()?.toAuthUser()
    }

    private fun FIRUser.toAuthUser(): AuthUser {
        return AuthUser(
            uid = this.uid() ?: "",
            email = this.email()
        )
    }
}
