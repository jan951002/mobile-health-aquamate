package com.poli.health.aquamate.intake.data.repository

import com.poli.health.aquamate.intake.domain.model.WaterIntake
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface WaterIntakeRepository {
    suspend fun registerIntake(intake: WaterIntake): Result<WaterIntake>
    suspend fun getLastIntake(): WaterIntake?
    fun getTodayIntakes(): Flow<List<WaterIntake>>
    fun getIntakesByDate(date: LocalDate): Flow<List<WaterIntake>>
    suspend fun getTodayTotalMl(): Int
    suspend fun deleteLastIntake(): Result<Unit>
}
