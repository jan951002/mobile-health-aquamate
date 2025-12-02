package com.poli.health.aquamate.intake.data.mapper

import com.poli.health.aquamate.intake.data.model.DailyIntakeEntity
import com.poli.health.aquamate.intake.domain.model.DailyIntake

interface DailyIntakeMapper {
    fun toDomain(entity: DailyIntakeEntity): DailyIntake
    fun toEntity(domain: DailyIntake): DailyIntakeEntity
}
