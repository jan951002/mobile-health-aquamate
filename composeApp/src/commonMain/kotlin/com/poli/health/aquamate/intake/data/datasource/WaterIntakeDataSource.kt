package com.poli.health.aquamate.intake.data.datasource

import com.poli.health.aquamate.intake.data.model.WaterIntakeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

internal interface WaterIntakeDataSource {
    suspend fun insert(intake: WaterIntakeEntity)
    suspend fun getLastIntake(): WaterIntakeEntity?
    fun getTodayIntakes(): Flow<List<WaterIntakeEntity>>
    fun getIntakesByDate(date: LocalDate): Flow<List<WaterIntakeEntity>>
    suspend fun deleteById(id: String)
    suspend fun getAllIntakes(): List<WaterIntakeEntity>
}
