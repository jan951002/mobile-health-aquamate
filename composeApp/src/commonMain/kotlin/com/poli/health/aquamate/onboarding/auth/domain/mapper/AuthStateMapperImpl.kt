package com.poli.health.aquamate.onboarding.auth.domain.mapper

import com.poli.health.aquamate.onboarding.auth.data.model.LoginResult
import com.poli.health.aquamate.onboarding.auth.domain.model.AuthState

internal class AuthStateMapperImpl(
    private val userMapper: UserMapper
) : AuthStateMapper {

    override fun toAuthState(loginResult: LoginResult?) = when (loginResult) {
        is LoginResult.Loading -> AuthState.Loading
        is LoginResult.Success -> AuthState.Authenticated(
            user = userMapper.toUser(loginResult.user)
        )

        is LoginResult.Error -> AuthState.NotAuthenticated(error = loginResult.message)
        null -> AuthState.NotAuthenticated()
    }
}
