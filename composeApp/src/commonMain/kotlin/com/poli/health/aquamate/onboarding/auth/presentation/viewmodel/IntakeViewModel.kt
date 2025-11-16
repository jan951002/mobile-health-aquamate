package com.poli.health.aquamate.onboarding.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poli.health.aquamate.onboarding.auth.domain.usecase.DeleteLastIntakeUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.GetLastIntakeUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.GetTodayTotalUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.RegisterWaterIntakeUseCase
import com.poli.health.aquamate.onboarding.auth.presentation.model.IntakeEvent
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IntakeViewModel(
    private val registerWaterIntakeUseCase: RegisterWaterIntakeUseCase,
    private val getLastIntakeUseCase: GetLastIntakeUseCase,
    private val deleteLastIntakeUseCase: DeleteLastIntakeUseCase,
    private val getTodayTotalUseCase: GetTodayTotalUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(IntakeState())
    val state: StateFlow<IntakeState> = _state.asStateFlow()

    init {
        loadInitialData()
    }

    fun onEvent(event: IntakeEvent) {
        when (event) {
            is IntakeEvent.RegisterPreset -> registerIntake(event.preset.ml)
            is IntakeEvent.RegisterCustom -> registerIntake(event.volumeMl)
            is IntakeEvent.UpdateCustomVolume -> updateCustomVolume(event.volumeMl)
            is IntakeEvent.DeleteLastIntake -> deleteLastIntake()
            is IntakeEvent.ClearError -> clearError()
            is IntakeEvent.ClearSuccess -> clearSuccess()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val lastIntake = getLastIntakeUseCase()
                val todayTotal = getTodayTotalUseCase()

                _state.update {
                    it.copy(
                        lastIntake = lastIntake,
                        todayTotal = todayTotal,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "${AquaMateStrings.Intake.ERROR_LOADING_DATA}: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun registerIntake(volumeMl: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            registerWaterIntakeUseCase(volumeMl)
                .onSuccess { intake ->
                    val newTotal = _state.value.todayTotal + volumeMl
                    _state.update {
                        it.copy(
                            lastIntake = intake,
                            todayTotal = newTotal,
                            isLoading = false,
                            successMessage = "$volumeMl${AquaMateStrings.Intake.VOLUME_REGISTERED}"
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            error = error.message ?: AquaMateStrings.Intake.ERROR_REGISTERING_INTAKE,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun updateCustomVolume(volumeMl: Int) {
        _state.update { it.copy(customVolume = volumeMl) }
    }

    private fun deleteLastIntake() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val lastVolume = _state.value.lastIntake?.volumeMl ?: 0

            deleteLastIntakeUseCase()
                .onSuccess {
                    val newTotal = maxOf(0, _state.value.todayTotal - lastVolume)
                    val newLastIntake = getLastIntakeUseCase()

                    _state.update {
                        it.copy(
                            lastIntake = newLastIntake,
                            todayTotal = newTotal,
                            isLoading = false,
                            successMessage = AquaMateStrings.Intake.RECORD_DELETED
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            error = error.message ?: AquaMateStrings.Intake.ERROR_DELETING_RECORD,
                            isLoading = false
                        )
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
