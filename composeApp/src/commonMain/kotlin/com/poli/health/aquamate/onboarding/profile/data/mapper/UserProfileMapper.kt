package com.poli.health.aquamate.onboarding.profile.data.mapper

import com.poli.health.aquamate.onboarding.profile.data.model.UserProfileEntity
import com.poli.health.aquamate.onboarding.profile.data.model.UserProfileLocalEntity
import com.poli.health.aquamate.onboarding.profile.domain.model.UserProfile

internal interface UserProfileMapper {
    fun toDomain(entity: UserProfileLocalEntity): UserProfile
    fun toDomain(entity: UserProfileEntity): UserProfile
    fun toLocalEntity(domain: UserProfile): UserProfileLocalEntity
    fun toRemoteEntity(domain: UserProfile): UserProfileEntity
}
