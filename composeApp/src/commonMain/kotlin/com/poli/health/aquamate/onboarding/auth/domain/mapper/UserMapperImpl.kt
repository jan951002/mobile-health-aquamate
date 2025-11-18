package com.poli.health.aquamate.onboarding.auth.domain.mapper

import com.poli.health.aquamate.onboarding.auth.data.model.AuthUser
import com.poli.health.aquamate.onboarding.auth.domain.model.User

internal class UserMapperImpl : UserMapper {

    override fun toUser(authUser: AuthUser) = User(
        id = authUser.uid,
        email = authUser.email
    )
}
