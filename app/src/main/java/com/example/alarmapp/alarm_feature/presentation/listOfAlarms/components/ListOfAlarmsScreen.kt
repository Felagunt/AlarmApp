package com.example.alarmapp.alarm_feature.presentation.listOfAlarms.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alarmapp.alarm_feature.presentation.UiEvent
import com.example.alarmapp.alarm_feature.presentation.listOfAlarms.AlarmEvent
import com.example.alarmapp.alarm_feature.presentation.listOfAlarms.ListOfAlarmsViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LisOfAlarmsScreen(
    alarmsViewModel: ListOfAlarmsViewModel = hiltViewModel()
) {

    val state = alarmsViewModel.state
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //TODO navigate
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
                .padding(16.dp)
        ) {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = 32.dp
            ) {
                Text(text = "Time until wakeup : ${alarmsViewModel.nearestAlarmAt}")
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
                            //TODO navigate
                        },
                        isEnabled = {
                            alarmsViewModel.onEvent(
                                AlarmEvent.OnEnableClick(alarm)
                            )
                        },
                        OnDeleteAlarmClick = {
                            alarmsViewModel.onEvent(
                                AlarmEvent.OnDeleteAlarmClick(alarm)
                            )
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Alarm deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    alarmsViewModel.onEvent(AlarmEvent.OnRestoreAlarmClick)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}