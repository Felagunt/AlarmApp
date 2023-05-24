package com.example.alarmapp.alarmManager_feature.presetation.fires_alarms

import com.example.alarmapp.alarmManager_feature.AlarmItem


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.alarmapp.alarmManager_feature.data.AndroidAlarmScheduler
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.presentation.listOfAlarms.AlarmEvent
import com.example.alarmapp.alarm_feature.util.UiEvent
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Composable
fun FireAlarmsScreen(
    //context: Context,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: FireAlarmsViewModel
) {
    val context = LocalContext.current
    val scheduler = AndroidAlarmScheduler(context)
    var alarmItem: Alarm? = null
    var secondsText by remember {
        mutableStateOf("")
    }
    var message by remember {
        mutableStateOf("")
    }

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEventFlow.collect{ event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                else -> Unit
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                alarmItem?.let(scheduler::cancel)
                viewModel.onEvent(FireAlarmsEvent.onCancel)
            }) {
                Text(text = "Cancel")
            }
            Button(onClick = {
                alarmItem = Alarm(
                    ringsTime = ZonedDateTime.now().plusMinutes(5),
                )
                alarmItem?.let(scheduler::scheduler)
                secondsText = ""
                message = ""
                Toast.makeText(context, "Awaiat 5 minutes", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "Wait 5 minutes")
            }
        }
    }
}