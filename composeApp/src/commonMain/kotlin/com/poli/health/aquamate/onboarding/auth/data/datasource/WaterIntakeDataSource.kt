package com.poli.health.aquamate.onboarding.auth.data.datasource

import com.poli.health.aquamate.onboarding.auth.domain.model.WaterIntake
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface WaterIntakeDataSource {
    suspend fun insert(intake: WaterIntake)
    suspend fun getLastIntake(): WaterIntake?
    fun getTodayIntakes(): Flow<List<WaterIntake>>
    fun getIntakesByDate(date: LocalDate): Flow<List<WaterIntake>>
    suspend fun deleteById(id: String)
    suspend fun getAllIntakes(): List<WaterIntake>
}