package com.poli.health.aquamate.intake.data.mapper

import com.poli.health.aquamate.intake.data.model.DailyIntakeEntity
import com.poli.health.aquamate.intake.data.model.IntakeRecordEntity
import com.poli.health.aquamate.intake.domain.model.DailyIntake
import com.poli.health.aquamate.intake.domain.model.WaterIntake
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal class DailyIntakeMapperImpl : DailyIntakeMapper {

    override fun toDomain(entity: DailyIntakeEntity): DailyIntake {
        val date = parseDate(entity.date)
        val intakes = entity.intakes.map { recordEntity ->
            val timestamp = Instant.fromEpochMilliseconds(recordEntity.timestampMillis)
            WaterIntake(
                id = recordEntity.id,
                volumeMl = recordEntity.volumeMl,
                timestamp = timestamp,
                date = timestamp.toLocalDateTime(TimeZone.currentSystemDefault())
            )
        }

        return DailyIntake(
            date = date,
            intakes = intakes,
            totalMl = entity.totalMl,
            goalMl = entity.goalMl
        )
    }

    override fun toEntity(domain: DailyIntake): DailyIntakeEntity {
        val dateString = formatDate(domain.date)
        val records = domain.intakes.map { intake ->
            IntakeRecordEntity(
                id = intake.id,
                volumeMl = intake.volumeMl,
                timestampMillis = intake.timestamp.toEpochMilliseconds()
            )
        }

        val now = kotlin.time.Clock.System.now().toEpochMilliseconds()

        return DailyIntakeEntity(
            date = dateString,
            intakes = records,
            totalMl = domain.totalMl,
            goalMl = domain.goalMl,
            createdAt = now,
            updatedAt = now
        )
    }

    private fun formatDate(date: LocalDate): String {
        val year = date.year.toString().padStart(4, '0')
        val month = date.monthNumber.toString().padStart(2, '0')
        val day = date.dayOfMonth.toString().padStart(2, '0')
        return "$year$month$day"
    }

    private fun parseDate(dateString: String): LocalDate {
        require(dateString.length == 8) { "Date string must be in YYYYMMDD format" }
        val year = dateString.substring(0, 4).toInt()
        val month = dateString.substring(4, 6).toInt()
        val day = dateString.substring(6, 8).toInt()
        return LocalDate(year, month, day)
    }
}
