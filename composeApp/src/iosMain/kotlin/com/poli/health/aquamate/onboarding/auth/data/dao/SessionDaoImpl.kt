package com.poli.health.aquamate.onboarding.auth.data.dao

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.poli.health.aquamate.onboarding.auth.data.model.UserSessionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SessionDaoImpl(
    private val dataStore: DataStore<Preferences>
) : SessionDao {

    companion object {
        private val KEY_USER_ID = stringPreferencesKey("user_id")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_PROFILE_COMPLETE = booleanPreferencesKey("profile_complete")
    }

    override suspend fun insertSession(session: UserSessionEntity) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = session.userId
            session.email?.let { preferences[KEY_EMAIL] = it }
            preferences[KEY_PROFILE_COMPLETE] = session.isProfileComplete
        }
    }

    override fun getSession(): Flow<UserSessionEntity?> {
        return dataStore.data.map { preferences ->
            val userId = preferences[KEY_USER_ID]
            if (userId != null) {
                UserSessionEntity(
                    userId = userId,
                    email = preferences[KEY_EMAIL],
                    isProfileComplete = preferences[KEY_PROFILE_COMPLETE] ?: false
                )
            } else {
                null
            }
        }
    }

    override suspend fun updateProfileComplete(isComplete: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_PROFILE_COMPLETE] = isComplete
        }
    }

    override suspend fun deleteSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
