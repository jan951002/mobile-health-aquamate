package com.poli.health.aquamate.onboarding.auth.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.poli.health.aquamate.commons.presentation.components.AquaMateButton
import com.poli.health.aquamate.commons.presentation.components.AquaMatePasswordField
import com.poli.health.aquamate.commons.presentation.components.AquaMateTextField
import com.poli.health.aquamate.onboarding.auth.presentation.model.AuthUiState
import com.poli.health.aquamate.onboarding.auth.presentation.viewmodel.AuthViewModel
import com.poli.health.aquamate.ui.theme.AquaMateDimensions
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit,
    viewModel: AuthViewModel = koinViewModel<AuthViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AuthUiState.Success -> onAuthSuccess()
            is AuthUiState.Error -> {
                snackbarHostState.showSnackbar(state.message)
                viewModel.clearError()
            }

            else -> Unit
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = AquaMateDimensions.ScreenMarginHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = AquaMateStrings.Auth.TITLE,
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingXL))

            when (uiState) {
                AuthUiState.Input -> {
                    AuthInputSection(
                        email = email,
                        password = password,
                        onEmailChange = { email = it },
                        onPasswordChange = { password = it },
                        onSignIn = { viewModel.onSignIn(email, password) },
                        onSignUp = { viewModel.onSignUp(email, password) }
                    )
                }

                AuthUiState.Authenticating -> {
                    AuthenticatingSection()
                }

                else -> Unit
            }
        }
    }
}

@Composable
private fun AuthInputSection(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignIn: () -> Unit,
    onSignUp: () -> Unit
) {
    AquaMateTextField(
        value = email,
        onValueChange = onEmailChange,
        label = AquaMateStrings.Auth.EMAIL_LABEL,
        leadingIcon = Icons.Default.Email,
        keyboardType = KeyboardType.Email
    )

    Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingM))

    AquaMatePasswordField(
        value = password,
        onValueChange = onPasswordChange,
        label = AquaMateStrings.Auth.PASSWORD_LABEL,
        showPasswordLabel = AquaMateStrings.Auth.SHOW_PASSWORD,
        hidePasswordLabel = AquaMateStrings.Auth.HIDE_PASSWORD
    )

    Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingL))

    val isFormValid = email.isNotBlank() && password.isNotBlank()

    AquaMateButton(
        text = AquaMateStrings.Auth.SIGN_IN_BUTTON,
        onClick = onSignIn,
        enabled = isFormValid,
        leadingIcon = Icons.AutoMirrored.Filled.Login
    )

    Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingM))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AquaMateStrings.Auth.OR_DIVIDER,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingM))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AquaMateStrings.Auth.SIGN_UP_TEXT,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        TextButton(
            onClick = onSignUp,
            enabled = isFormValid,
            modifier = Modifier.padding(vertical = 0.dp)
        ) {
            Text(
                text = AquaMateStrings.Auth.SIGN_UP_LINK,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isFormValid) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                }
            )
        }
    }
}

@Composable
private fun AuthenticatingSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingM))
        Text(
            text = AquaMateStrings.Auth.AUTHENTICATING,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
