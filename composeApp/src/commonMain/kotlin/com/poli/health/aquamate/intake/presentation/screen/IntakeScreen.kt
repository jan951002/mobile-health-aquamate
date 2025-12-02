package com.poli.health.aquamate.intake.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.poli.health.aquamate.intake.domain.model.PresetVolume
import com.poli.health.aquamate.intake.presentation.components.DailyProgressCard
import com.poli.health.aquamate.intake.presentation.components.DateNavigator
import com.poli.health.aquamate.intake.presentation.components.IntakeHistoryList
import com.poli.health.aquamate.intake.presentation.components.VolumeSelector
import com.poli.health.aquamate.intake.presentation.components.WeeklyStatsCard
import com.poli.health.aquamate.intake.presentation.model.IntakeEvent
import com.poli.health.aquamate.intake.presentation.viewmodel.IntakeState
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import com.poli.health.aquamate.ui.theme.dimensions
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun IntakeScreen(
    state: IntakeState = IntakeState(),
    onEvent: (IntakeEvent) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            onEvent(IntakeEvent.ClearError)
        }
    }

    LaunchedEffect(state.successMessage) {
        state.successMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            onEvent(IntakeEvent.ClearSuccess)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = AquaMateStrings.Intake.TITLE,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        if (state.isLoading) {
            LoadingIndicator()
        } else {
            IntakeContent(
                state = state,
                onEvent = onEvent,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun IntakeContent(
    state: IntakeState,
    onEvent: (IntakeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.dimensions.ScreenMarginHorizontal),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingL)
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpacingS))

        DateNavigator(
            selectedDate = state.selectedDate,
            onPreviousDay = {
                val previousDate = state.selectedDate.minus(1, DateTimeUnit.DAY)
                onEvent(IntakeEvent.SelectDate(previousDate))
            },
            onNextDay = {
                val nextDate = state.selectedDate.plus(1, DateTimeUnit.DAY)
                onEvent(IntakeEvent.SelectDate(nextDate))
            }
        )

        DailyProgressCard(
            currentMl = state.dailyIntake?.totalMl ?: 0,
            goalMl = state.dailyGoalMl,
            progressPercentage = state.progressPercentage
        )

        RegisterSection(
            state = state,
            onEvent = onEvent
        )

        IntakeHistoryList(
            intakes = state.dailyIntake?.intakes ?: emptyList(),
            onDeleteIntake = { intakeId ->
                onEvent(IntakeEvent.DeleteIntake(intakeId))
            }
        )

        WeeklyStatsCard(
            weeklyStats = state.weeklyStats
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpacingM))
    }
}

@Composable
private fun RegisterSection(
    state: IntakeState,
    onEvent: (IntakeEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingM)
    ) {
        Text(
            text = AquaMateStrings.Intake.REGISTER_TITLE,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        VolumeSelector(
            volumeMl = state.customVolume,
            onIncrementVolume = { onEvent(IntakeEvent.IncrementVolume) },
            onDecrementVolume = { onEvent(IntakeEvent.DecrementVolume) }
        )

        PresetButtons(onEvent = onEvent)

        RegisterButton(
            volumeMl = state.customVolume,
            onEvent = onEvent
        )
    }
}

@Composable
private fun PresetButtons(onEvent: (IntakeEvent) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingS)
    ) {
        PresetVolume.entries.forEach { preset ->
            Button(
                onClick = { onEvent(IntakeEvent.RegisterPreset(preset)) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = preset.label)
            }
        }
    }
}

@Composable
private fun RegisterButton(
    volumeMl: Int,
    onEvent: (IntakeEvent) -> Unit
) {
    Button(
        onClick = { onEvent(IntakeEvent.RegisterCustom(volumeMl)) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = AquaMateStrings.Intake.REGISTER_BUTTON,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
