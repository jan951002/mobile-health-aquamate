package com.poli.health.aquamate.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import aquamate.composeapp.generated.resources.Res
import aquamate.composeapp.generated.resources.profile
import org.jetbrains.compose.resources.stringResource

@Composable
fun LanguageScreen(nav: NavController) {

    var selectedLanguage by remember { mutableStateOf("es") }

    Column(
        Modifier.fillMaxSize().padding(24.dp)
    ) {

        Text(
            text = stringResource(Res.string.profile),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(16.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .clickable { selectedLanguage = "es" }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedLanguage == "es",
                onClick = { selectedLanguage = "es" }
            )
            Text("Español", Modifier.padding(start = 8.dp))
        }

        Row(
            Modifier
                .fillMaxWidth()
                .clickable { selectedLanguage = "en" }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedLanguage == "en",
                onClick = { selectedLanguage = "en" }
            )
            Text("English", Modifier.padding(start = 8.dp))
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { nav.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(Res.string.profile))
        }
    }
}
