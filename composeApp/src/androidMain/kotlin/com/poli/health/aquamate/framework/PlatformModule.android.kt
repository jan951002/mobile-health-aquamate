package com.poli.health.aquamate.framework

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.poli.health.aquamate.onboarding.auth.data.AndroidAuthFirebaseDataSource
import com.poli.health.aquamate.onboarding.auth.data.FirebaseAuthErrorHandler
import com.poli.health.aquamate.onboarding.auth.data.FirebaseAuthErrorHandlerImpl
import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule: Module = module {

    single<FirebaseAuth> { Firebase.auth }

    factory<FirebaseAuthErrorHandler> { FirebaseAuthErrorHandlerImpl() }

    single<CoroutineDispatcher>(named("ioDispatcher")) { Dispatchers.IO }

    single<CoroutineDispatcher>(named("mainDispatcher")) { Dispatchers.Main }

    single<AuthRemoteDataSource> {
        AndroidAuthFirebaseDataSource(
            firebaseAuth = get(),
            ioDispatcher = get(named("ioDispatcher")),
            errorHandler = get()
        )
    }
}

