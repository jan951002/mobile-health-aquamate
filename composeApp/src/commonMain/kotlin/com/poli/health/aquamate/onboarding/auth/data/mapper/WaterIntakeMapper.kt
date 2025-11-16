package com.poli.health.aquamate.onboarding.auth.data.mapper

import com.poli.health.aquamate.onboarding.auth.data.model.WaterIntakeEntity
import com.poli.health.aquamate.onboarding.auth.domain.model.WaterIntake

internal interface WaterIntakeMapper {
    fun toDomain(entity: WaterIntakeEntity): WaterIntake
    fun toEntity(domain: WaterIntake): WaterIntakeEntity
}
