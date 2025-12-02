package com.poli.health.aquamate.onboarding.profile.data.mapper

import com.poli.health.aquamate.onboarding.profile.data.model.UserProfileEntity
import com.poli.health.aquamate.onboarding.profile.data.model.UserProfileLocalEntity
import com.poli.health.aquamate.onboarding.profile.domain.model.ActivityLevel
import com.poli.health.aquamate.onboarding.profile.domain.model.Gender
import com.poli.health.aquamate.onboarding.profile.domain.model.Height
import com.poli.health.aquamate.onboarding.profile.domain.model.HeightUnit
import com.poli.health.aquamate.onboarding.profile.domain.model.UserProfile
import com.poli.health.aquamate.onboarding.profile.domain.model.Weight
import com.poli.health.aquamate.onboarding.profile.domain.model.WeightUnit
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
internal class UserProfileMapperImpl : UserProfileMapper {

    override fun toDomain(entity: UserProfileLocalEntity): UserProfile {
        val birthDate = Instant.fromEpochMilliseconds(entity.birthDateMillis)
            .toLocalDateTime(TimeZone.UTC).date

        return UserProfile(
            userId = entity.userId,
            weight = Weight(entity.weightKg, WeightUnit.KILOGRAM),
            height = Height(entity.heightCm, HeightUnit.CENTIMETER),
            birthDate = birthDate,
            gender = Gender.valueOf(entity.gender),
            activityLevel = ActivityLevel.valueOf(entity.activityLevel),
            dailyWaterGoalMl = entity.dailyWaterGoalMl,
            bmi = entity.bmi,
            bmr = entity.bmr,
            totalEnergyExpenditure = entity.totalEnergyExpenditure,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    override fun toDomain(entity: UserProfileEntity): UserProfile {
        val birthDate = Instant.fromEpochMilliseconds(entity.birthDateMillis)
            .toLocalDateTime(TimeZone.UTC).date

        return UserProfile(
            userId = entity.userId,
            weight = Weight(entity.weightKg, WeightUnit.KILOGRAM),
            height = Height(entity.heightCm, HeightUnit.CENTIMETER),
            birthDate = birthDate,
            gender = Gender.valueOf(entity.gender),
            activityLevel = ActivityLevel.valueOf(entity.activityLevel),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    override fun toLocalEntity(domain: UserProfile): UserProfileLocalEntity {
        val birthDateMillis = domain.birthDate.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()

        return UserProfileLocalEntity(
            userId = domain.userId,
            weightKg = domain.weightKg,
            heightCm = domain.heightCm,
            birthDateMillis = birthDateMillis,
            gender = domain.gender.name,
            activityLevel = domain.activityLevel.name,
            dailyWaterGoalMl = domain.dailyWaterGoalMl,
            bmi = domain.bmi,
            bmr = domain.bmr,
            totalEnergyExpenditure = domain.totalEnergyExpenditure,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
            lastSyncedAt = Clock.System.now().toEpochMilliseconds()
        )
    }

    override fun toRemoteEntity(domain: UserProfile): UserProfileEntity {
        val birthDateMillis = domain.birthDate.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()

        return UserProfileEntity(
            userId = domain.userId,
            weightKg = domain.weightKg,
            heightCm = domain.heightCm,
            birthDateMillis = birthDateMillis,
            gender = domain.gender.name,
            activityLevel = domain.activityLevel.name,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}
