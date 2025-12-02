package com.poli.health.aquamate.intake.data.datasource

import com.poli.health.aquamate.intake.data.model.DailyIntakeEntity

interface WaterIntakeRemoteDataSource {
    suspend fun saveDailyIntake(userId: String, entity: DailyIntakeEntity): Result<Unit>
    suspend fun getDailyIntake(userId: String, date: String): Result<DailyIntakeEntity?>
    suspend fun getDailyIntakesForRange(
        userId: String,
        startDate: String,
        endDate: String
    ): Result<List<DailyIntakeEntity>>
}
