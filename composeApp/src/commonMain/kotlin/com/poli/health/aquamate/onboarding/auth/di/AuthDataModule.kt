package com.poli.health.aquamate.onboarding.auth.di

import com.poli.health.aquamate.onboarding.auth.data.error.FirebaseAuthErrorHandlerImpl
import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthLocalDataSource
import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthLocalDataSourceImpl
import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthRemoteDataSource
import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthFirebaseDataSource
import com.poli.health.aquamate.onboarding.auth.data.error.FirebaseAuthErrorHandler
import com.poli.health.aquamate.onboarding.auth.data.repository.AuthRepository
import com.poli.health.aquamate.onboarding.auth.data.repository.AuthRepositoryImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authDataModule: Module = module {

    single { Firebase.auth }

    factory<FirebaseAuthErrorHandler> { FirebaseAuthErrorHandlerImpl() }

    single<AuthRemoteDataSource> {
        AuthFirebaseDataSource(
            firebaseAuth = get(),
            ioDispatcher = get(named("ioDispatcher")),
            errorHandler = get()
        )
    }

    single<AuthLocalDataSource> {
        AuthLocalDataSourceImpl(
            sessionDao = get(),
            ioDispatcher = get(named("ioDispatcher"))
        )
    }

    factory<AuthRepository> {
        AuthRepositoryImpl(
            authRemoteDataSource = get(),
            authLocalDataSource = get()
        )
    }
}
