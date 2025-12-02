package com.poli.health.aquamate.onboarding.profile.presentation.model

import com.poli.health.aquamate.onboarding.profile.domain.model.ActivityLevel
import com.poli.health.aquamate.onboarding.profile.domain.model.Gender
import kotlinx.datetime.LocalDate

sealed class UserProfileEvent {
    data class OnWeightChanged(val weight: String) : UserProfileEvent()
    data class OnHeightChanged(val height: String) : UserProfileEvent()
    data class OnBirthDateSelected(val birthDate: LocalDate) : UserProfileEvent()
    data class OnGenderSelected(val gender: Gender) : UserProfileEvent()
    data class OnActivityLevelSelected(val activityLevel: ActivityLevel) : UserProfileEvent()
    data object OnSaveProfile : UserProfileEvent()
}
