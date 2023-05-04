package com.example.alarmapp.alarm_feature.presentation.listOfAlarms.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alarmapp.alarm_feature.presentation.listOfAlarms.AlarmEvent
import com.example.alarmapp.alarm_feature.presentation.listOfAlarms.ListOfAlarmsViewModel
import com.example.alarmapp.alarm_feature.util.UiEvent

@Composable
fun LisOfAlarmsScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    alarmsViewModel: ListOfAlarmsViewModel = hiltViewModel()
) {

    val state = alarmsViewModel.state
    val scaffoldState = rememberScaffoldState()
    val nextAlarmTime by remember {//TODO
        mutableStateOf(
            alarmsViewModel.nextAlarmTime
        )
    }
    //val nat = alarmsViewModel.nextAlarmTime

    LaunchedEffect(key1 = true) {
        alarmsViewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if(result == SnackbarResult.ActionPerformed) {
                        alarmsViewModel.onEvent(AlarmEvent.OnRestoreAlarmClick)
                    }
                }
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          alarmsViewModel.onEvent(AlarmEvent.OnAddAlarmClick)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add alarm"
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        MaterialTheme.colors.primary,
                        RectangleShape
                    )
            ) {
                Text(
                    text = "Time until wakeup : $nextAlarmTime",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(state.alarms) { alarm ->
                    AlarmListItem(
                        alarm = alarm,
                        onEvent = alarmsViewModel::onEvent,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                alarmsViewModel.onEvent(AlarmEvent.OnAlarmClick(alarm))
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}