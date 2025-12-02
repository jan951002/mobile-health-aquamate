package com.poli.health.aquamate.intake.data.repository

import com.poli.health.aquamate.intake.data.datasource.WaterIntakeDataSource
import com.poli.health.aquamate.intake.data.datasource.WaterIntakeRemoteDataSource
import com.poli.health.aquamate.intake.data.mapper.DailyIntakeMapper
import com.poli.health.aquamate.intake.data.mapper.WaterIntakeMapper
import com.poli.health.aquamate.intake.domain.model.DailyIntake
import com.poli.health.aquamate.intake.domain.model.WaterIntake
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

internal class WaterIntakeRepositoryImpl(
    private val localDataSource: WaterIntakeDataSource,
    private val remoteDataSource: WaterIntakeRemoteDataSource,
    private val waterIntakeMapper: WaterIntakeMapper,
    private val dailyIntakeMapper: DailyIntakeMapper
) : WaterIntakeRepository {

    override suspend fun registerIntake(
        intake: WaterIntake,
        userId: String,
        dailyGoalMl: Int
    ): Result<WaterIntake> {
        return try {
            val entity = waterIntakeMapper.toEntity(intake)
            localDataSource.insert(entity)

            val date = intake.date.date
            val dailyIntake = getDailyIntake(userId, date) ?: DailyIntake(
                date = date,
                intakes = emptyList(),
                totalMl = 0,
                goalMl = dailyGoalMl
            )

            val updatedIntakes = dailyIntake.intakes + intake
            val updatedTotalMl = dailyIntake.totalMl + intake.volumeMl

            val updatedDailyIntake = dailyIntake.copy(
                intakes = updatedIntakes,
                totalMl = updatedTotalMl
            )

            val dailyEntity = dailyIntakeMapper.toEntity(updatedDailyIntake)
            val saveResult = remoteDataSource.saveDailyIntake(userId, dailyEntity)

            if (saveResult.isFailure) {
                return Result.failure(
                    saveResult.exceptionOrNull() ?: Exception("Failed to save to Firestore")
                )
            }

            Result.success(intake)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDailyIntake(userId: String, date: LocalDate): DailyIntake? {
        val dateString = formatDate(date)
        return try {
            val result = remoteDataSource.getDailyIntake(userId, dateString)
            result.getOrNull()?.let { dailyIntakeMapper.toDomain(it) }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getDailyIntakesForRange(
        userId: String,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<DailyIntake> {
        val startDateString = formatDate(startDate)
        val endDateString = formatDate(endDate)
        return try {
            val result =
                remoteDataSource.getDailyIntakesForRange(userId, startDateString, endDateString)
            result.getOrNull()?.map { dailyIntakeMapper.toDomain(it) } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun deleteIntakeById(
        userId: String,
        date: LocalDate,
        intakeId: String
    ): Result<Unit> {
        return try {
            val dailyIntake = getDailyIntake(userId, date)
            if (dailyIntake != null) {
                val updatedIntakes = dailyIntake.intakes.filter { it.id != intakeId }
                val deletedIntake = dailyIntake.intakes.find { it.id == intakeId }

                if (deletedIntake != null) {
                    val updatedTotalMl = dailyIntake.totalMl - deletedIntake.volumeMl
                    val updatedDailyIntake = dailyIntake.copy(
                        intakes = updatedIntakes,
                        totalMl = updatedTotalMl
                    )

                    val dailyEntity = dailyIntakeMapper.toEntity(updatedDailyIntake)
                    val saveResult = remoteDataSource.saveDailyIntake(userId, dailyEntity)

                    if (saveResult.isFailure) {
                        return Result.failure(
                            saveResult.exceptionOrNull()
                                ?: Exception("Failed to delete from Firestore")
                        )
                    }

                    val entityToDelete = waterIntakeMapper.toEntity(deletedIntake)
                    localDataSource.deleteById(entityToDelete.id)

                    Result.success(Unit)
                } else {
                    Result.failure(NoSuchElementException(AquaMateStrings.Intake.NO_RECORDS_TO_DELETE))
                }
            } else {
                Result.failure(NoSuchElementException(AquaMateStrings.Intake.NO_RECORDS_TO_DELETE))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLastIntake(): WaterIntake? {
        return try {
            localDataSource.getLastIntake()?.let { waterIntakeMapper.toDomain(it) }
        } catch (e: Exception) {
            null
        }
    }

    override fun getTodayIntakes(): Flow<List<WaterIntake>> {
        return localDataSource.getTodayIntakes().map { entities ->
            entities.map { waterIntakeMapper.toDomain(it) }
        }
    }

    override fun getIntakesByDate(date: LocalDate): Flow<List<WaterIntake>> {
        return localDataSource.getIntakesByDate(date).map { entities ->
            entities.map { waterIntakeMapper.toDomain(it) }
        }
    }

    override suspend fun getTodayTotalMl(): Int {
        return try {
            localDataSource.getTodayIntakes()
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
            val lastIntake = localDataSource.getLastIntake()
            if (lastIntake != null) {
                localDataSource.deleteById(lastIntake.id)
                Result.success(Unit)
            } else {
                Result.failure(NoSuchElementException(AquaMateStrings.Intake.NO_RECORDS_TO_DELETE))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun formatDate(date: LocalDate): String {
        val year = date.year.toString().padStart(4, '0')
        val month = date.monthNumber.toString().padStart(2, '0')
        val day = date.dayOfMonth.toString().padStart(2, '0')
        return "$year$month$day"
    }
}
