package com.poli.health.aquamate.onboarding.profile.domain.exception

import com.poli.health.aquamate.onboarding.profile.domain.model.ProfileError

/**
 * Domain exception for user profile operations.
 * Wraps a ProfileError which can be mapped to localized messages in the presentation layer.
 *
 * @property error The specific error that occurred
 * @property cause The underlying exception, if any
 */
class ProfileException(
    val error: ProfileError,
    cause: Throwable? = null
) : Exception(error::class.simpleName, cause)
