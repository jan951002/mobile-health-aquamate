package com.poli.health.aquamate.intake.data.repository

import com.poli.health.aquamate.intake.domain.model.DailyIntake
import com.poli.health.aquamate.intake.domain.model.WaterIntake
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface WaterIntakeRepository {
    suspend fun registerIntake(intake: WaterIntake, userId: String, dailyGoalMl: Int): Result<WaterIntake>
    suspend fun getDailyIntake(userId: String, date: LocalDate): DailyIntake?
    suspend fun getDailyIntakesForRange(userId: String, startDate: LocalDate, endDate: LocalDate): List<DailyIntake>
    suspend fun deleteIntakeById(userId: String, date: LocalDate, intakeId: String): Result<Unit>
    suspend fun getLastIntake(): WaterIntake?
    fun getTodayIntakes(): Flow<List<WaterIntake>>
    fun getIntakesByDate(date: LocalDate): Flow<List<WaterIntake>>
    suspend fun getTodayTotalMl(): Int
    suspend fun deleteLastIntake(): Result<Unit>
}
