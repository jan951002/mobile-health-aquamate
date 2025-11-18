package com.poli.health.aquamate.framework

import cocoapods.FirebaseAuth.FIRAuth
import com.poli.health.aquamate.onboarding.auth.data.FirebaseAuthErrorHandler
import com.poli.health.aquamate.onboarding.auth.data.FirebaseAuthErrorHandlerImpl
import com.poli.health.aquamate.onboarding.auth.data.IOSAuthFirebaseDataSource
import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthRemoteDataSource
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

@OptIn(ExperimentalForeignApi::class)
actual val platformModule: Module = module {

    single<FIRAuth> { FIRAuth.auth() }

    factory<FirebaseAuthErrorHandler> { FirebaseAuthErrorHandlerImpl() }

    single<CoroutineDispatcher>(named("ioDispatcher")) { Dispatchers.IO }

    single<CoroutineDispatcher>(named("mainDispatcher")) { Dispatchers.Main }

    single<AuthRemoteDataSource> {
        IOSAuthFirebaseDataSource(
            firebaseAuth = get(),
            ioDispatcher = get(named("ioDispatcher")),
            errorHandler = get()
        )
    }
}

