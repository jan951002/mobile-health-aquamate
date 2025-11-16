package com.poli.health.aquamate.onboarding.auth.data.datasource

import com.poli.health.aquamate.onboarding.auth.data.model.WaterIntakeEntity
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
internal class WaterIntakeLocalDataSource : WaterIntakeDataSource {
    private val intakes = MutableStateFlow<List<WaterIntakeEntity>>(emptyList())

    override suspend fun insert(intake: WaterIntakeEntity) {
        val currentList = intakes.value.toMutableList()
        currentList.add(intake)
        intakes.value = currentList.sortedByDescending { it.timestampMillis }
    }

    override suspend fun getLastIntake(): WaterIntakeEntity? {
        return intakes.value.maxByOrNull { it.timestampMillis }
    }

    override fun getTodayIntakes(): Flow<List<WaterIntakeEntity>> {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        return intakes.map { list ->
            list.filter {
                val entityDate = Instant.fromEpochMilliseconds(it.timestampMillis)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date
                entityDate == today
            }
        }
    }

    override fun getIntakesByDate(date: LocalDate): Flow<List<WaterIntakeEntity>> {
        return intakes.map { list ->
            list.filter {
                val entityDate = Instant.fromEpochMilliseconds(it.timestampMillis)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date
                entityDate == date
            }
        }
    }

    override suspend fun deleteById(id: String) {
        val currentList = intakes.value.toMutableList()
        currentList.removeAll { it.id == id }
        intakes.value = currentList
    }

    override suspend fun getAllIntakes(): List<WaterIntakeEntity> {
        return intakes.value
    }
}
