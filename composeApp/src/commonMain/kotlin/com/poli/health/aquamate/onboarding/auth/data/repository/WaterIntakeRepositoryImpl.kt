package com.poli.health.aquamate.onboarding.auth.data.repository

import com.poli.health.aquamate.onboarding.auth.data.datasource.WaterIntakeDataSource
import com.poli.health.aquamate.onboarding.auth.domain.model.WaterIntake
import com.poli.health.aquamate.onboarding.auth.data.repository.WaterIntakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class WaterIntakeRepositoryImpl(
    private val dataSource: WaterIntakeDataSource
) : WaterIntakeRepository {

    override suspend fun registerIntake(intake: WaterIntake): Result<WaterIntake> {
        return try {
            dataSource.insert(intake)
            Result.success(intake)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLastIntake(): WaterIntake? {
        return try {
            dataSource.getLastIntake()
        } catch (e: Exception) {
            null
        }
    }

    override fun getTodayIntakes(): Flow<List<WaterIntake>> {
        return dataSource.getTodayIntakes()
    }

    override fun getIntakesByDate(date: LocalDate): Flow<List<WaterIntake>> {
        return dataSource.getIntakesByDate(date)
    }

    override suspend fun getTodayTotalMl(): Int {
        return try {
            dataSource.getTodayIntakes()
                .map { intakes -> intakes.sumOf { it.volumeMl } }
                .let { flow ->
                    var total = 0
                    flow.collect { total = it }
                    total
                }
        } catch (e: Exception) {
            0
        }
    }

    override suspend fun deleteLastIntake(): Result<Unit> {
        return try {
            val lastIntake = dataSource.getLastIntake()
            if (lastIntake != null) {
                dataSource.deleteById(lastIntake.id)
                Result.success(Unit)
            } else {
                Result.failure(NoSuchElementException("No hay registros para eliminar"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}