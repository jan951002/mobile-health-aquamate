package com.poli.health.aquamate.intake.data.mapper

import com.poli.health.aquamate.intake.data.model.WaterIntakeEntity
import com.poli.health.aquamate.intake.domain.model.WaterIntake

internal interface WaterIntakeMapper {
    fun toDomain(entity: WaterIntakeEntity): WaterIntake
    fun toEntity(domain: WaterIntake): WaterIntakeEntity
}
