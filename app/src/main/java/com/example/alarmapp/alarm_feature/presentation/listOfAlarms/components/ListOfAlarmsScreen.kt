package com.example.alarmapp.alarm_feature.presentation.listOfAlarms.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.presentation.UiEvent
import com.example.alarmapp.alarm_feature.presentation.listOfAlarms.AlarmEvent
import com.example.alarmapp.alarm_feature.presentation.listOfAlarms.AlarmsState
import com.example.alarmapp.core.presentation.ScreenRoutes
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZonedDateTime


@Composable
fun LisOfAlarmsScreen(
    //navController: NavController,
    //alarmsViewModel: ListOfAlarmsViewModel = hiltViewModel()
    state: AlarmsState,
    nextAlarmTime: LocalDateTime? = null,
    //nextAlarmTime:
    uiEvent: SharedFlow<UiEvent>,
    onEvent: (AlarmEvent) -> Unit,
    navigate: () -> Unit
) {

    //val state = alarmsViewModel.state
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

//    val nextAlarmTime by remember {
//        mutableStateOf(
//            alarmsViewModel.nextAlarmTime
//        )
//    }

    LaunchedEffect(key1 = true) {
        uiEvent.collectLatest {
            when(it) {
                is UiEvent.Navigate -> {
                    navigate()
                }

                else -> {}
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigate()
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
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = 32.dp
            ) {
                Text(text = "Time until wakeup : $nextAlarmTime")
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(state.alarms) { alarm ->
                    AlarmListItem(
                        alarm = alarm,
                        OnItemClick = {
                            navigate()
                        },
                        isEnabled = {
                            onEvent(
                                AlarmEvent.OnEnableClick(alarm)
                            )
                        },
                        OnDeleteAlarmClick = {
                            onEvent(
                                AlarmEvent.OnDeleteAlarmClick(alarm)
                            )
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Alarm deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    onEvent(AlarmEvent.OnRestoreAlarmClick)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListOfAlarmsScreenPreview() {
    LisOfAlarmsScreen(
        state = AlarmsState(
            alarms = listOf(
                Alarm(
                    alarmId = null,
                    ringsTime = ZonedDateTime.now().plusMinutes(40),
                    isEnabled = true,
                    isVibration = false,
                    ringMelody = 2.toString()
                )
            )
        ),
        onEvent = {},
        uiEvent = emptyFlow<UiEvent>() as SharedFlow<UiEvent>
    ) {
        ScreenRoutes.AddEditAlarmScreen.route
    }
}