package com.poli.health.aquamate.onboarding.auth.domain.mapper

import com.poli.health.aquamate.onboarding.auth.data.model.LoginResult
import com.poli.health.aquamate.onboarding.auth.domain.model.AuthState

internal interface AuthStateMapper {

    fun toAuthState(loginResult: LoginResult?): AuthState
}
