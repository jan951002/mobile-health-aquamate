package com.poli.health.aquamate.onboarding.profile.domain.usecase

import com.poli.health.aquamate.onboarding.profile.domain.exception.ProfileException
import com.poli.health.aquamate.onboarding.profile.domain.model.Gender
import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileError

internal class CalculateBmrUseCaseImpl : CalculateBmrUseCase {

    override fun invoke(weightKg: Double, age: Int, gender: Gender): Double {
        if (weightKg <= 0) {
            throw ProfileException(ProfileError.Validation.WeightInvalid)
        }
        if (age <= 0) {
            throw ProfileException(ProfileError.Validation.AgeInvalid)
        }

        return when {
            age in 18..59 && gender == Gender.MALE -> {
                (weightKg * 15.057) + 692.2
            }
            age in 18..59 && gender == Gender.FEMALE -> {
                (weightKg * 14.818) + 486.6
            }
            age >= 60 && gender == Gender.MALE -> {
                (weightKg * 11.711) + 587.7
            }
            age >= 60 && gender == Gender.FEMALE -> {
                (weightKg * 9.082) + 658.5
            }
            else -> {
                (weightKg * 15.057) + 692.2
            }
        }
    }
}
