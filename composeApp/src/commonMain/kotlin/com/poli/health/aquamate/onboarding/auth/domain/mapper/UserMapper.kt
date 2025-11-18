package com.poli.health.aquamate.onboarding.auth.domain.mapper

import com.poli.health.aquamate.onboarding.auth.data.model.AuthUser
import com.poli.health.aquamate.onboarding.auth.domain.model.User

internal interface UserMapper {

    fun toUser(authUser: AuthUser): User
}
