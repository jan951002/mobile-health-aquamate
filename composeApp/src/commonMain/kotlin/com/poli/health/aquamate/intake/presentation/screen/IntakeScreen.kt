package com.poli.health.aquamate.intake.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
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
import com.poli.health.aquamate.commons.presentation.components.AquaMateButton
import com.poli.health.aquamate.intake.presentation.components.DailyProgressCard
import com.poli.health.aquamate.intake.presentation.components.DateNavigator
import com.poli.health.aquamate.intake.presentation.components.IntakeHistoryItem
import com.poli.health.aquamate.intake.presentation.components.VolumeSelector
import com.poli.health.aquamate.intake.presentation.components.WeeklyStatsCard
import com.poli.health.aquamate.intake.presentation.model.IntakeEvent
import com.poli.health.aquamate.intake.presentation.viewmodel.IntakeState
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import com.poli.health.aquamate.ui.theme.dimensions
import com.poli.health.aquamate.ui.theme.extendedColors
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Preview
@Composable
fun IntakeScreen(
    state: IntakeState = IntakeState(),
    onEvent: (IntakeEvent) -> Unit = {}
) {
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date
    val isToday = state.selectedDate == today
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
            SnackbarHost(snackbarHostState) { data ->
                val isError = state.error != null
                Snackbar(
                    snackbarData = data,
                    containerColor = if (isError) {
                        MaterialTheme.colorScheme.errorContainer
                    } else {
                        MaterialTheme.extendedColors.successContainer
                    },
                    contentColor = if (isError) {
                        MaterialTheme.colorScheme.onErrorContainer
                    } else {
                        MaterialTheme.extendedColors.onSuccessContainer
                    }
                )
            }
        }
    ) { paddingValues ->
        if (state.isLoading) {
            LoadingIndicator()
        } else {
            IntakeContent(
                state = state,
                onEvent = onEvent,
                isToday = isToday,
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
    isToday: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = MaterialTheme.dimensions.ScreenMarginHorizontal,
                vertical = MaterialTheme.dimensions.ScreenMarginTop
            ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingM)
    ) {
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

        WeeklyStatsCard(
            weeklyStats = state.weeklyStats
        )

        DailyProgressCard(
            currentMl = state.dailyIntake?.totalMl ?: 0,
            goalMl = state.dailyGoalMl,
            progressPercentage = state.progressPercentage
        )

        if (isToday) {
            RegisterCard(
                state = state,
                onEvent = onEvent
            )
        }

        HistoryCard(
            intakes = state.dailyIntake?.intakes ?: emptyList(),
            onDeleteIntake = { intakeId ->
                onEvent(IntakeEvent.DeleteIntake(intakeId))
            }
        )
    }
}

@Composable
private fun RegisterCard(
    state: IntakeState,
    onEvent: (IntakeEvent) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.dimensions.ElevationMedium
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimensions.CardPadding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingM)
        ) {
            SectionHeader(
                icon = Icons.Default.Add,
                title = AquaMateStrings.Intake.REGISTER_TITLE
            )

            VolumeSelector(
                volumeMl = state.customVolume,
                onIncrementVolume = { onEvent(IntakeEvent.IncrementVolume) },
                onDecrementVolume = { onEvent(IntakeEvent.DecrementVolume) }
            )

            AquaMateButton(
                text = AquaMateStrings.Intake.REGISTER_BUTTON,
                onClick = { onEvent(IntakeEvent.RegisterCustom(state.customVolume)) },
                leadingIcon = Icons.Default.WaterDrop
            )
        }
    }
}

@Composable
private fun HistoryCard(
    intakes: List<com.poli.health.aquamate.intake.domain.model.WaterIntake>,
    onDeleteIntake: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.dimensions.ElevationMedium
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimensions.CardPadding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingM)
        ) {
            SectionHeader(
                icon = Icons.Default.WaterDrop,
                title = AquaMateStrings.Intake.HISTORY_TITLE
            )

            if (intakes.isEmpty()) {
                EmptyHistoryMessage()
            } else {
                IntakeList(
                    intakes = intakes,
                    onDeleteIntake = onDeleteIntake
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingS)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(MaterialTheme.dimensions.IconSizeM)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun EmptyHistoryMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimensions.SpacingM),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = AquaMateStrings.Intake.HISTORY_EMPTY,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun IntakeList(
    intakes: List<com.poli.health.aquamate.intake.domain.model.WaterIntake>,
    onDeleteIntake: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingS)
    ) {
        intakes.forEach { intake ->
            IntakeHistoryItem(
                intake = intake,
                onDeleteClick = { onDeleteIntake(intake.id) }
            )
        }
    }
}
