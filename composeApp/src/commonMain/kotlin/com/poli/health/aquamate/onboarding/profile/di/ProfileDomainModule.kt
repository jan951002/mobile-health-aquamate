package com.poli.health.aquamate.onboarding.profile.di

import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateBmiUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateBmiUseCaseImpl
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateBmrUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateBmrUseCaseImpl
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateTotalEnergyExpenditureUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateTotalEnergyExpenditureUseCaseImpl
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateWaterIntakeUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateWaterIntakeUseCaseImpl
import com.poli.health.aquamate.onboarding.profile.domain.usecase.GetUserProfileUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.GetUserProfileUseCaseImpl
import com.poli.health.aquamate.onboarding.profile.domain.usecase.HasUserProfileUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.HasUserProfileUseCaseImpl
import com.poli.health.aquamate.onboarding.profile.domain.usecase.SaveUserProfileUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.SaveUserProfileUseCaseImpl
import com.poli.health.aquamate.onboarding.profile.domain.validator.UserProfileValidator
import com.poli.health.aquamate.onboarding.profile.domain.validator.UserProfileValidatorImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val profileDomainModule: Module = module {

    factory<UserProfileValidator> {
        UserProfileValidatorImpl()
    }

    factory<CalculateWaterIntakeUseCase> {
        CalculateWaterIntakeUseCaseImpl()
    }

    factory<CalculateBmiUseCase> {
        CalculateBmiUseCaseImpl()
    }

    factory<CalculateBmrUseCase> {
        CalculateBmrUseCaseImpl()
    }

    factory<CalculateTotalEnergyExpenditureUseCase> {
        CalculateTotalEnergyExpenditureUseCaseImpl()
    }

    factory<SaveUserProfileUseCase> {
        SaveUserProfileUseCaseImpl(
            repository = get(),
            validator = get()
        )
    }

    factory<GetUserProfileUseCase> {
        GetUserProfileUseCaseImpl(
            repository = get()
        )
    }

    factory<HasUserProfileUseCase> {
        HasUserProfileUseCaseImpl(
            repository = get()
        )
    }
}
