package com.poli.health.aquamate.onboarding.profile.data.repository

import com.poli.health.aquamate.onboarding.profile.data.datasource.UserProfileLocalDataSource
import com.poli.health.aquamate.onboarding.profile.data.datasource.UserProfileRemoteDataSource
import com.poli.health.aquamate.onboarding.profile.data.mapper.UserProfileMapper
import com.poli.health.aquamate.onboarding.profile.domain.model.UserProfile
import com.poli.health.aquamate.onboarding.profile.domain.repository.UserProfileRepository
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateBmiUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateBmrUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateTotalEnergyExpenditureUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateWaterIntakeUseCase
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalTime::class)
internal class UserProfileRepositoryImpl(
    private val remoteDataSource: UserProfileRemoteDataSource,
    private val localDataSource: UserProfileLocalDataSource,
    private val mapper: UserProfileMapper,
    private val calculateWaterIntakeUseCase: CalculateWaterIntakeUseCase,
    private val calculateBmiUseCase: CalculateBmiUseCase,
    private val calculateBmrUseCase: CalculateBmrUseCase,
    private val calculateTotalEnergyExpenditureUseCase: CalculateTotalEnergyExpenditureUseCase
) : UserProfileRepository {

    override suspend fun saveUserProfile(profile: UserProfile): Result<Unit> {
        val currentTime = Clock.System.now().toEpochMilliseconds()

        val dailyWaterGoal = calculateWaterIntakeUseCase(
            weightKg = profile.weightKg,
            activityLevel = profile.activityLevel
        )
        val bmi = calculateBmiUseCase(
            weightKg = profile.weightKg,
            heightCm = profile.heightCm
        )
        val basalMetabolicRate = calculateBmrUseCase(
            weightKg = profile.weightKg,
            age = profile.age,
            gender = profile.gender
        )
        val totalEnergy = calculateTotalEnergyExpenditureUseCase(
            basalMetabolicRate = basalMetabolicRate,
            activityLevel = profile.activityLevel
        )

        val enrichedProfile = profile.copy(
            dailyWaterGoalMl = dailyWaterGoal,
            bmi = bmi,
            bmr = basalMetabolicRate,
            totalEnergyExpenditure = totalEnergy,
            createdAt = if (profile.createdAt == 0L) currentTime else profile.createdAt,
            updatedAt = currentTime
        )

        val localEntity = mapper.toLocalEntity(enrichedProfile)
        localDataSource.saveProfile(localEntity)

        return try {
            val remoteEntity = mapper.toRemoteEntity(enrichedProfile)
            remoteDataSource.saveUserProfile(remoteEntity)
        } catch (e: Exception) {
            Result.success(Unit)
        }
    }

    override fun getUserProfile(userId: String): Flow<UserProfile?> {
        return localDataSource.getProfile()
            .map { localEntity ->
                localEntity?.let { mapper.toDomain(it) }
            }
    }

    override suspend fun hasUserProfile(userId: String): Boolean {
        val localProfile = localDataSource.getProfile().first()
        if (localProfile != null) {
            return true
        }
        
        return try {
            val remoteResult = remoteDataSource.getUserProfile(userId)
            val remoteProfile = remoteResult.getOrNull()
            if (remoteProfile != null) {
                syncFromRemote(userId)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun syncFromRemote(userId: String): Result<Unit> {
        return try {
            val remoteResult = remoteDataSource.getUserProfile(userId)
            remoteResult.getOrNull()?.let { remoteEntity ->
                val domainProfile = mapper.toDomain(remoteEntity)

                val dailyWaterGoal = calculateWaterIntakeUseCase(
                    weightKg = domainProfile.weightKg,
                    activityLevel = domainProfile.activityLevel
                )
                val bmi = calculateBmiUseCase(
                    weightKg = domainProfile.weightKg,
                    heightCm = domainProfile.heightCm
                )
                val basalMetabolicRate = calculateBmrUseCase(
                    weightKg = domainProfile.weightKg,
                    age = domainProfile.age,
                    gender = domainProfile.gender
                )
                val totalEnergy = calculateTotalEnergyExpenditureUseCase(
                    basalMetabolicRate = basalMetabolicRate,
                    activityLevel = domainProfile.activityLevel
                )

                val enrichedProfile = domainProfile.copy(
                    dailyWaterGoalMl = dailyWaterGoal,
                    bmi = bmi,
                    bmr = basalMetabolicRate,
                    totalEnergyExpenditure = totalEnergy
                )

                val localEntity = mapper.toLocalEntity(enrichedProfile)
                localDataSource.saveProfile(localEntity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUserProfile() {
        localDataSource.deleteProfile()
    }
}
