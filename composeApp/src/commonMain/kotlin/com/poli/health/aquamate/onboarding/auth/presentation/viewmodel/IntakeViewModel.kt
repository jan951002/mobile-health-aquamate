package com.poli.health.aquamate.onboarding.auth.presentation.viewmodel

import com.poli.health.aquamate.onboarding.auth.domain.usecase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IntakeViewModel(
    private val registerWaterIntakeUseCase: RegisterWaterIntakeUseCase,
    private val getLastIntakeUseCase: GetLastIntakeUseCase,
    private val deleteLastIntakeUseCase: DeleteLastIntakeUseCase,
    private val getTodayTotalUseCase: GetTodayTotalUseCase,
    private val coroutineScope: CoroutineScope
) {
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
        coroutineScope.launch {
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
                        error = "Error al cargar datos: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun registerIntake(volumeMl: Int) {
        coroutineScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            registerWaterIntakeUseCase(volumeMl)
                .onSuccess { intake ->
                    val newTotal = _state.value.todayTotal + volumeMl
                    _state.update {
                        it.copy(
                            lastIntake = intake,
                            todayTotal = newTotal,
                            isLoading = false,
                            successMessage = "¡${volumeMl}ml registrados!"
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            error = error.message ?: "Error al registrar consumo",
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
        coroutineScope.launch {
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
                            successMessage = "Registro eliminado"
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            error = error.message ?: "Error al eliminar registro",
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