package com.poli.health.aquamate.onboarding.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poli.health.aquamate.onboarding.auth.domain.usecase.GetCurrentUserIdUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.UpdateProfileCompleteUseCase
import com.poli.health.aquamate.onboarding.profile.domain.exception.ProfileException
import com.poli.health.aquamate.onboarding.profile.domain.model.Height
import com.poli.health.aquamate.onboarding.profile.domain.model.HeightUnit
import com.poli.health.aquamate.onboarding.profile.domain.model.UserProfile
import com.poli.health.aquamate.onboarding.profile.domain.model.Weight
import com.poli.health.aquamate.onboarding.profile.domain.model.WeightUnit
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateBmiUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.CalculateWaterIntakeUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.SaveUserProfileUseCase
import com.poli.health.aquamate.onboarding.profile.presentation.mapper.ProfileErrorMapper
import com.poli.health.aquamate.onboarding.profile.presentation.model.UserProfileEvent
import com.poli.health.aquamate.onboarding.profile.presentation.model.UserProfileUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileViewModel(
    private val saveUserProfileUseCase: SaveUserProfileUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val calculateWaterIntakeUseCase: CalculateWaterIntakeUseCase,
    private val calculateBmiUseCase: CalculateBmiUseCase,
    private val updateProfileCompleteUseCase: UpdateProfileCompleteUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileUiState())
    val state: StateFlow<UserProfileUiState> = _state.asStateFlow()

    fun onEvent(event: UserProfileEvent) {
        when (event) {
            is UserProfileEvent.OnWeightChanged -> {
                _state.update { it.copy(weight = event.weight, errorMessage = null) }
                updatePreviews()
            }
            is UserProfileEvent.OnHeightChanged -> {
                _state.update { it.copy(height = event.height, errorMessage = null) }
                updatePreviews()
            }
            is UserProfileEvent.OnBirthDateSelected -> {
                _state.update { it.copy(birthDate = event.birthDate, errorMessage = null) }
            }
            is UserProfileEvent.OnGenderSelected -> {
                _state.update { it.copy(gender = event.gender, errorMessage = null) }
            }
            is UserProfileEvent.OnActivityLevelSelected -> {
                _state.update { it.copy(activityLevel = event.activityLevel, errorMessage = null) }
                updatePreviews()
            }
            is UserProfileEvent.OnSaveProfile -> {
                saveProfile()
            }
        }
    }

    private fun updatePreviews() {
        val currentState = _state.value

        val weight = currentState.weight.toDoubleOrNull()
        val height = currentState.height.toDoubleOrNull()
        val activityLevel = currentState.activityLevel

        if (weight != null && height != null && activityLevel != null) {
            try {
                val waterGoal = calculateWaterIntakeUseCase(weight, activityLevel)
                val bmi = calculateBmiUseCase(weight, height)

                _state.update {
                    it.copy(
                        dailyWaterGoalPreview = waterGoal,
                        bmiPreview = bmi
                    )
                }
            } catch (e: ProfileException) {
                _state.update {
                    it.copy(
                        dailyWaterGoalPreview = 0,
                        bmiPreview = 0.0
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        dailyWaterGoalPreview = 0,
                        bmiPreview = 0.0
                    )
                }
            }
        } else {
            _state.update {
                it.copy(
                    dailyWaterGoalPreview = 0,
                    bmiPreview = 0.0
                )
            }
        }
    }

    private fun saveProfile() {
        viewModelScope.launch(ioDispatcher) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val currentState = _state.value
                val userId = getCurrentUserIdUseCase().first() ?: throw IllegalStateException("User not logged in")

                val weight = currentState.weight.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Invalid weight")
                val height = currentState.height.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Invalid height")
                val birthDate = currentState.birthDate
                    ?: throw IllegalArgumentException("Please select birth date")
                val gender = currentState.gender
                    ?: throw IllegalArgumentException("Please select gender")
                val activityLevel = currentState.activityLevel
                    ?: throw IllegalArgumentException("Please select activity level")

                val profile = UserProfile(
                    userId = userId,
                    weight = Weight(weight, WeightUnit.KILOGRAM),
                    height = Height(height, HeightUnit.CENTIMETER),
                    birthDate = birthDate,
                    gender = gender,
                    activityLevel = activityLevel
                )

                val result = saveUserProfileUseCase(profile)

                if (result.isSuccess) {
                    updateProfileCompleteUseCase(true)

                    withContext(mainDispatcher) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isSaveSuccessful = true
                            )
                        }
                    }
                } else {
                    withContext(mainDispatcher) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.exceptionOrNull()?.message ?: "Failed to save profile"
                            )
                        }
                    }
                }
            } catch (e: ProfileException) {
                withContext(mainDispatcher) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = ProfileErrorMapper.toMessage(e.error)
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(mainDispatcher) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "An error occurred"
                        )
                    }
                }
            }
        }
    }
}
