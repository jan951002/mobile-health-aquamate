package com.poli.health.aquamate.onboarding.profile.data.dao

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.poli.health.aquamate.onboarding.profile.data.model.UserProfileLocalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class UserProfileDaoImpl(
    private val dataStore: DataStore<Preferences>
) : UserProfileDao {

    companion object {
        private val KEY_USER_ID = stringPreferencesKey("profile_user_id")
        private val KEY_WEIGHT_KG = doublePreferencesKey("profile_weight_kg")
        private val KEY_HEIGHT_CM = doublePreferencesKey("profile_height_cm")
        private val KEY_BIRTH_DATE_MILLIS = longPreferencesKey("profile_birth_date_millis")
        private val KEY_GENDER = stringPreferencesKey("profile_gender")
        private val KEY_ACTIVITY_LEVEL = stringPreferencesKey("profile_activity_level")
        private val KEY_DAILY_WATER_GOAL_ML = intPreferencesKey("profile_daily_water_goal_ml")
        private val KEY_BMI = doublePreferencesKey("profile_bmi")
        private val KEY_BMR = doublePreferencesKey("profile_bmr")
        private val KEY_TOTAL_ENERGY = doublePreferencesKey("profile_total_energy")
        private val KEY_CREATED_AT = longPreferencesKey("profile_created_at")
        private val KEY_UPDATED_AT = longPreferencesKey("profile_updated_at")
        private val KEY_LAST_SYNCED_AT = longPreferencesKey("profile_last_synced_at")
    }

    override suspend fun saveProfile(profile: UserProfileLocalEntity) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = profile.userId
            preferences[KEY_WEIGHT_KG] = profile.weightKg
            preferences[KEY_HEIGHT_CM] = profile.heightCm
            preferences[KEY_BIRTH_DATE_MILLIS] = profile.birthDateMillis
            preferences[KEY_GENDER] = profile.gender
            preferences[KEY_ACTIVITY_LEVEL] = profile.activityLevel
            preferences[KEY_DAILY_WATER_GOAL_ML] = profile.dailyWaterGoalMl
            preferences[KEY_BMI] = profile.bmi
            preferences[KEY_BMR] = profile.bmr
            preferences[KEY_TOTAL_ENERGY] = profile.totalEnergyExpenditure
            preferences[KEY_CREATED_AT] = profile.createdAt
            preferences[KEY_UPDATED_AT] = profile.updatedAt
            preferences[KEY_LAST_SYNCED_AT] = profile.lastSyncedAt
        }
    }

    override fun getProfile(): Flow<UserProfileLocalEntity?> {
        return dataStore.data.map { preferences ->
            val userId = preferences[KEY_USER_ID]
            if (userId != null) {
                UserProfileLocalEntity(
                    userId = userId,
                    weightKg = preferences[KEY_WEIGHT_KG] ?: 0.0,
                    heightCm = preferences[KEY_HEIGHT_CM] ?: 0.0,
                    birthDateMillis = preferences[KEY_BIRTH_DATE_MILLIS] ?: 0L,
                    gender = preferences[KEY_GENDER] ?: "",
                    activityLevel = preferences[KEY_ACTIVITY_LEVEL] ?: "",
                    dailyWaterGoalMl = preferences[KEY_DAILY_WATER_GOAL_ML] ?: 0,
                    bmi = preferences[KEY_BMI] ?: 0.0,
                    bmr = preferences[KEY_BMR] ?: 0.0,
                    totalEnergyExpenditure = preferences[KEY_TOTAL_ENERGY] ?: 0.0,
                    createdAt = preferences[KEY_CREATED_AT] ?: 0L,
                    updatedAt = preferences[KEY_UPDATED_AT] ?: 0L,
                    lastSyncedAt = preferences[KEY_LAST_SYNCED_AT] ?: 0L
                )
            } else {
                null
            }
        }
    }

    override suspend fun deleteProfile() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
