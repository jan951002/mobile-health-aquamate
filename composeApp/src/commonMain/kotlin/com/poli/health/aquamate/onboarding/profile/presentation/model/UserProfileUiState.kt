package com.poli.health.aquamate.onboarding.profile.presentation.model

import com.poli.health.aquamate.onboarding.profile.domain.model.ActivityLevel
import com.poli.health.aquamate.onboarding.profile.domain.model.Gender
import kotlinx.datetime.LocalDate

data class UserProfileUiState(
    val weight: String = "",
    val height: String = "",
    val birthDate: LocalDate? = null,
    val gender: Gender? = null,
    val activityLevel: ActivityLevel? = null,
    val dailyWaterGoalPreview: Int = 0,
    val bmiPreview: Double = 0.0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSaveSuccessful: Boolean = false,
    val validationErrors: Map<String, String> = emptyMap()
)
