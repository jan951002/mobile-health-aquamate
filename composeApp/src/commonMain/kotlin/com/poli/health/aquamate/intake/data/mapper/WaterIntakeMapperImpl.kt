package com.poli.health.aquamate.intake.data.mapper

import com.poli.health.aquamate.intake.data.model.WaterIntakeEntity
import com.poli.health.aquamate.intake.domain.model.WaterIntake
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
internal class WaterIntakeMapperImpl : WaterIntakeMapper {
    override fun toDomain(entity: WaterIntakeEntity): WaterIntake {
        return WaterIntake(
            id = entity.id,
            volumeMl = entity.volumeMl,
            timestamp = Instant.fromEpochMilliseconds(entity.timestampMillis)
        )
    }

    override fun toEntity(domain: WaterIntake): WaterIntakeEntity {
        return WaterIntakeEntity(
            id = domain.id,
            volumeMl = domain.volumeMl,
            timestampMillis = domain.timestamp.toEpochMilliseconds()
        )
    }
}
