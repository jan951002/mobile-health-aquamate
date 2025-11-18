package com.poli.health.aquamate.onboarding.auth.di

import com.poli.health.aquamate.onboarding.auth.domain.mapper.AuthStateMapper
import com.poli.health.aquamate.onboarding.auth.domain.mapper.AuthStateMapperImpl
import com.poli.health.aquamate.onboarding.auth.domain.mapper.UserMapper
import com.poli.health.aquamate.onboarding.auth.domain.mapper.UserMapperImpl
import com.poli.health.aquamate.onboarding.auth.domain.usecase.IsUserLoggedInUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.IsUserLoggedInUseCaseImpl
import com.poli.health.aquamate.onboarding.auth.domain.usecase.ObserveAuthStateUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.ObserveAuthStateUseCaseImpl
import com.poli.health.aquamate.onboarding.auth.domain.usecase.SignInWithEmailUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.SignInWithEmailUseCaseImpl
import com.poli.health.aquamate.onboarding.auth.domain.usecase.SignOutUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.SignOutUseCaseImpl
import com.poli.health.aquamate.onboarding.auth.domain.usecase.SignUpWithEmailUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.SignUpWithEmailUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val authDomainModule: Module = module {

    factory<UserMapper> {
        UserMapperImpl()
    }

    factory<AuthStateMapper> {
        AuthStateMapperImpl(
            userMapper = get()
        )
    }

    factory<SignInWithEmailUseCase> {
        SignInWithEmailUseCaseImpl(
            loginRepository = get(),
            authStateMapper = get()
        )
    }

    factory<SignUpWithEmailUseCase> {
        SignUpWithEmailUseCaseImpl(
            loginRepository = get(),
            authStateMapper = get()
        )
    }

    factory<ObserveAuthStateUseCase> {
        ObserveAuthStateUseCaseImpl(
            loginRepository = get(),
            authStateMapper = get()
        )
    }

    factory<SignOutUseCase> {
        SignOutUseCaseImpl(
            loginRepository = get()
        )
    }

    factory<IsUserLoggedInUseCase> {
        IsUserLoggedInUseCaseImpl(
            loginRepository = get()
        )
    }
}
