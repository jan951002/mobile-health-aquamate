package com.poli.health.aquamate.intake.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poli.health.aquamate.intake.domain.usecase.DeleteIntakeByIdUseCase
import com.poli.health.aquamate.intake.domain.usecase.GetDailyIntakeUseCase
import com.poli.health.aquamate.intake.domain.usecase.GetUserDailyGoalUseCase
import com.poli.health.aquamate.intake.domain.usecase.GetWeeklyStatsUseCase
import com.poli.health.aquamate.intake.domain.usecase.RegisterWaterIntakeUseCase
import com.poli.health.aquamate.intake.presentation.model.IntakeEvent
import com.poli.health.aquamate.onboarding.auth.domain.usecase.GetCurrentUserIdUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.SignOutUseCase
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
class IntakeViewModel(
    private val registerWaterIntakeUseCase: RegisterWaterIntakeUseCase,
    private val getDailyIntakeUseCase: GetDailyIntakeUseCase,
    private val getUserDailyGoalUseCase: GetUserDailyGoalUseCase,
    private val getWeeklyStatsUseCase: GetWeeklyStatsUseCase,
    private val deleteIntakeByIdUseCase: DeleteIntakeByIdUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(IntakeState())
    val state: StateFlow<IntakeState> = _state.asStateFlow()

    private companion object {
        const val VOLUME_INCREMENT = 50
        const val MIN_VOLUME = 50
        const val MAX_VOLUME = 2000
    }

    init {
        loadInitialData()
    }

    fun onEvent(event: IntakeEvent) {
        when (event) {
            is IntakeEvent.RegisterPreset -> registerIntake(event.preset.ml)
            is IntakeEvent.RegisterCustom -> registerIntake(event.volumeMl)
            is IntakeEvent.UpdateCustomVolume -> updateCustomVolume(event.volumeMl)
            is IntakeEvent.IncrementVolume -> incrementVolume()
            is IntakeEvent.DecrementVolume -> decrementVolume()
            is IntakeEvent.SelectDate -> selectDate(event.date)
            is IntakeEvent.DeleteIntake -> deleteIntake(event.intakeId)
            is IntakeEvent.LoadWeeklyStats -> loadWeeklyStats()
            is IntakeEvent.DeleteLastIntake -> deleteLastIntake()
            is IntakeEvent.Logout -> logout()
            is IntakeEvent.ClearError -> clearError()
            is IntakeEvent.ClearSuccess -> clearSuccess()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val userId = getCurrentUserIdUseCase().first()
                    ?: throw IllegalStateException("User not logged in")

                val dailyGoal = getUserDailyGoalUseCase(userId)
                val today = Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date
                val dailyIntake = getDailyIntakeUseCase(userId, today)

                val progressPercentage = if (dailyGoal > 0 && dailyIntake != null) {
                    ((dailyIntake.totalMl.toFloat() / dailyGoal) * 100).toInt()
                } else {
                    0
                }

                _state.update {
                    it.copy(
                        selectedDate = today,
                        dailyIntake = dailyIntake,
                        dailyGoalMl = dailyGoal,
                        progressPercentage = progressPercentage,
                        isLoading = false
                    )
                }

                loadWeeklyStats()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Error loading data: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun registerIntake(volumeMl: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val userId = getCurrentUserIdUseCase().first()
                    ?: throw IllegalStateException("User not logged in")
                val dailyGoal = _state.value.dailyGoalMl

                registerWaterIntakeUseCase(volumeMl, userId, dailyGoal)
                    .onSuccess {
                        val today = Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                        val updatedDailyIntake = getDailyIntakeUseCase(userId, today)
                        val updatedWeeklyStats = getWeeklyStatsUseCase(userId)

                        val progressPercentage = if (dailyGoal > 0 && updatedDailyIntake != null) {
                            ((updatedDailyIntake.totalMl.toFloat() / dailyGoal) * 100).toInt()
                        } else {
                            0
                        }

                        _state.update {
                            it.copy(
                                dailyIntake = updatedDailyIntake,
                                weeklyStats = updatedWeeklyStats,
                                progressPercentage = progressPercentage,
                                isLoading = false,
                                successMessage = "${volumeMl}ml registered successfully"
                            )
                        }
                    }
                    .onFailure { error ->
                        _state.update {
                            it.copy(
                                error = error.message ?: "Error registering intake",
                                isLoading = false
                            )
                        }
                    }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Error: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun updateCustomVolume(volumeMl: Int) {
        val clampedVolume = volumeMl.coerceIn(MIN_VOLUME, MAX_VOLUME)
        _state.update { it.copy(customVolume = clampedVolume) }
    }

    private fun incrementVolume() {
        val currentVolume = _state.value.customVolume
        val newVolume = (currentVolume + VOLUME_INCREMENT).coerceAtMost(MAX_VOLUME)
        _state.update { it.copy(customVolume = newVolume) }
    }

    private fun decrementVolume() {
        val currentVolume = _state.value.customVolume
        val newVolume = (currentVolume - VOLUME_INCREMENT).coerceAtLeast(MIN_VOLUME)
        _state.update { it.copy(customVolume = newVolume) }
    }

    private fun selectDate(date: kotlinx.datetime.LocalDate) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, selectedDate = date) }

            try {
                val userId = getCurrentUserIdUseCase().first()
                    ?: throw IllegalStateException("User not logged in")
                val dailyIntake = getDailyIntakeUseCase(userId, date)
                val dailyGoal = _state.value.dailyGoalMl

                val progressPercentage = if (dailyGoal > 0 && dailyIntake != null) {
                    ((dailyIntake.totalMl.toFloat() / dailyGoal) * 100).toInt()
                } else {
                    0
                }

                _state.update {
                    it.copy(
                        dailyIntake = dailyIntake,
                        progressPercentage = progressPercentage,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Error loading date: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun deleteIntake(intakeId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val userId = getCurrentUserIdUseCase().first()
                    ?: throw IllegalStateException("User not logged in")
                val date = _state.value.selectedDate

                deleteIntakeByIdUseCase(userId, date, intakeId)
                    .onSuccess {
                        val updatedDailyIntake = getDailyIntakeUseCase(userId, date)
                        val updatedWeeklyStats = getWeeklyStatsUseCase(userId)
                        val dailyGoal = _state.value.dailyGoalMl

                        val progressPercentage = if (dailyGoal > 0 && updatedDailyIntake != null) {
                            ((updatedDailyIntake.totalMl.toFloat() / dailyGoal) * 100).toInt()
                        } else {
                            0
                        }

                        _state.update {
                            it.copy(
                                dailyIntake = updatedDailyIntake,
                                weeklyStats = updatedWeeklyStats,
                                progressPercentage = progressPercentage,
                                isLoading = false,
                                successMessage = "Intake deleted successfully"
                            )
                        }
                    }
                    .onFailure { error ->
                        _state.update {
                            it.copy(
                                error = error.message ?: "Error deleting intake",
                                isLoading = false
                            )
                        }
                    }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Error: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun deleteLastIntake() {
        viewModelScope.launch {
            val dailyIntake = _state.value.dailyIntake
            val lastIntake = dailyIntake?.intakes?.lastOrNull()

            if (lastIntake != null) {
                deleteIntake(lastIntake.id)
            } else {
                _state.update {
                    it.copy(error = "No intake to delete")
                }
            }
        }
    }

    private fun loadWeeklyStats() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingStats = true) }

            try {
                val userId = getCurrentUserIdUseCase().first()
                    ?: throw IllegalStateException("User not logged in")
                val weeklyStats = getWeeklyStatsUseCase(userId)

                _state.update {
                    it.copy(
                        weeklyStats = weeklyStats,
                        isLoadingStats = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Error loading stats: ${e.message}",
                        isLoadingStats = false
                    )
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            try {
                signOutUseCase()
                _state.update {
                    it.copy(isLoggedOut = true)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = "Error signing out: ${e.message}")
                }
            }
        }
    }

    private fun clearError() {
        _state.update { it.copy(error = null) }
    }

    private fun clearSuccess() {
        _state.update { it.copy(successMessage = null) }
    }
}
