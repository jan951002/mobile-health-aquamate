package com.poli.health.aquamate.onboarding.auth.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.poli.health.aquamate.onboarding.auth.presentation.model.LoginUiState
import com.poli.health.aquamate.ui.components.AquaMatePasswordField
import com.poli.health.aquamate.ui.components.AquaMateTextField
import com.poli.health.aquamate.onboarding.auth.presentation.viewmodel.LoginViewModel
import com.poli.health.aquamate.ui.theme.AquaMateDimensions
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel<LoginViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is LoginUiState.Success -> onLoginSuccess()
            is LoginUiState.Error -> {
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
                text = AquaMateStrings.Login.TITLE,
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingXL))

            AquaMateTextField(
                value = email,
                onValueChange = { email = it },
                label = AquaMateStrings.Login.EMAIL_LABEL,
                leadingIcon = Icons.Default.Email,
                keyboardType = KeyboardType.Email,
                enabled = uiState !is LoginUiState.Loading
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingM))

            AquaMatePasswordField(
                value = password,
                onValueChange = { password = it },
                label = AquaMateStrings.Login.PASSWORD_LABEL,
                enabled = uiState !is LoginUiState.Loading,
                showPasswordLabel = AquaMateStrings.Login.SHOW_PASSWORD,
                hidePasswordLabel = AquaMateStrings.Login.HIDE_PASSWORD
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingL))

            Button(
                onClick = { viewModel.signIn(email, password) },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is LoginUiState.Loading && email.isNotBlank() && password.isNotBlank()
            ) {
                if (uiState is LoginUiState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Login,
                        contentDescription = "Login icon"
                    )
                    Spacer(modifier = Modifier.width(AquaMateDimensions.SpacingXS))
                    Text(AquaMateStrings.Login.SIGN_IN_BUTTON)
                }
            }
        }
    }
}
