package com.poli.health.aquamate.onboarding.profile.domain.validator

import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileValidationResult
import com.poli.health.aquamate.onboarding.profile.domain.model.UserProfile

interface UserProfileValidator {
    fun validate(profile: UserProfile): ProfileValidationResult
}
