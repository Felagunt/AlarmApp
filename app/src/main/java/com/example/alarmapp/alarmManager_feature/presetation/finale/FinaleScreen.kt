package com.example.alarmapp.alarmManager_feature.presetation

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alarmapp.alarmManager_feature.presetation.finale.FinaleViewModel

@Composable
fun FinaleScreen(
    viewModel: FinaleViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {

    val state by viewModel.currentTime.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Good Morning",
                style = MaterialTheme.typography.h4
            )
            Text(
                text = "Now: $state",
                style = MaterialTheme.typography.h5
            )
        }
    }
}