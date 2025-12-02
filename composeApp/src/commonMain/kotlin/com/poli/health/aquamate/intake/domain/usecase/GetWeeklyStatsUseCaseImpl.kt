package com.poli.health.aquamate.intake.domain.usecase

import com.poli.health.aquamate.intake.data.repository.WaterIntakeRepository
import com.poli.health.aquamate.intake.domain.model.WeeklyStats
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
internal class GetWeeklyStatsUseCaseImpl(
    private val repository: WaterIntakeRepository
) : GetWeeklyStatsUseCase {

    override suspend fun invoke(userId: String): WeeklyStats {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val sevenDaysAgo = today.minus(7, DateTimeUnit.DAY)

        val dailyIntakes = repository.getDailyIntakesForRange(userId, sevenDaysAgo, today)

        val totalMl = dailyIntakes.sumOf { it.totalMl }
        val averageMl = if (dailyIntakes.isNotEmpty()) totalMl / dailyIntakes.size else 0

        val daysWithGoalAchieved = dailyIntakes.count { dailyIntake ->
            dailyIntake.totalMl >= dailyIntake.goalMl
        }

        return WeeklyStats(
            averageMl = averageMl,
            daysWithGoalAchieved = daysWithGoalAchieved,
            totalDays = dailyIntakes.size
        )
    }
}
