package com.poli.health.aquamate.intake.data.repository

import com.poli.health.aquamate.intake.data.datasource.WaterIntakeDataSource
import com.poli.health.aquamate.intake.data.mapper.WaterIntakeMapper
import com.poli.health.aquamate.intake.domain.model.WaterIntake
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

internal class WaterIntakeRepositoryImpl(
    private val dataSource: WaterIntakeDataSource,
    private val mapper: WaterIntakeMapper
) : WaterIntakeRepository {

    override suspend fun registerIntake(intake: WaterIntake): Result<WaterIntake> {
        return try {
            val entity = mapper.toEntity(intake)
            dataSource.insert(entity)
            Result.success(intake)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLastIntake(): WaterIntake? {
        return try {
            dataSource.getLastIntake()?.let { mapper.toDomain(it) }
        } catch (e: Exception) {
            null
        }
    }

    override fun getTodayIntakes(): Flow<List<WaterIntake>> {
        return dataSource.getTodayIntakes().map { entities ->
            entities.map { mapper.toDomain(it) }
        }
    }

    override fun getIntakesByDate(date: LocalDate): Flow<List<WaterIntake>> {
        return dataSource.getIntakesByDate(date).map { entities ->
            entities.map { mapper.toDomain(it) }
        }
    }

    override suspend fun getTodayTotalMl(): Int {
        return try {
            dataSource.getTodayIntakes()
                .map { entities -> entities.sumOf { it.volumeMl } }
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
                Result.failure(NoSuchElementException(AquaMateStrings.Intake.NO_RECORDS_TO_DELETE))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
