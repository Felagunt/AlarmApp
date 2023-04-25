package com.example.alarmapp.alarm_feature.presentation.addEditAlarm.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.alarmapp.alarm_feature.presentation.UiEvent
import com.example.alarmapp.alarm_feature.presentation.addEditAlarm.AddEditAlarmState
import com.example.alarmapp.alarm_feature.presentation.addEditAlarm.AddEditEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun AddEditAlarmScreen(
    //navController: NavController,
    //addEditViewModel: AddEditViewModel,
    state: AddEditAlarmState,
    alarmEvent: Flow<UiEvent>,
    onEvent: (AddEditEvent) -> Unit,
    navigate: () -> Unit
) {
    //val state = addEditViewModel.state

    var hours by remember {
        mutableStateOf(LocalDateTime.now().hour)
    }
    val minutes by remember {
        mutableStateOf(LocalDateTime.now().minute)
    }

    val scaffoldState = rememberScaffoldState()

    val melodyOptions = listOf("opt1", "opt2", "opt3")
    val categoryExpanded by remember { mutableStateOf(false) }
    val categorySelectedOptionText
            by remember { mutableStateOf(state.alarm!!.ringMelody) }
    val melodyDropDownMenuPar by remember {
        mutableStateOf(
            DropDownMenuParameter(
                options = melodyOptions,
                expanded = categoryExpanded,
                selectedOptionText = categorySelectedOptionText,
                backgroundColor = Color.LightGray
            )
        )
    }
    LaunchedEffect(key1 = true) {
        alarmEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.SaveAlarm -> {
                    //TODO navigate
                }
                is UiEvent.Navigate -> {
                    navigate()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(AddEditEvent.SaveAlarm)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save Alarm"
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.onSurface)
                .padding(16.dp)
        ) {
            state.alarm?.let { alarm ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(modifier = Modifier) {
                        CounterComponent(count = hours, style = MaterialTheme.typography.h3)
                        Button(onClick ={
                            hours ++
                            onEvent(
                                AddEditEvent.OnChangeHours(
                                    hours = hours
                                )
                            )
                        }
                        ) {
                            Text(text = "Increase +1")
                        }
                    }
                    Text(text = ":", style = MaterialTheme.typography.h3)
                    Column(modifier = Modifier) {
                        CounterComponent(count = minutes, style = MaterialTheme.typography.h3)
                        Button(onClick = {
                            minutes + 10
                            onEvent(
                                AddEditEvent.OnChangeMinutes(
                                    minutes = minutes
                                )
                            )
                        }
                        ) {
                            Text(text = "Increase +10")
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CheckBoxComponent(
                        alarm = alarm,
                        textLabel = "Vibration: ",
                        checked = alarm.isVibration,
                        onCheckChange = {
                            onEvent(AddEditEvent.OnCheckedEnabled(alarm.isVibration))
                        }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.onSecondary)
                            .border(
                                width = 3.dp,
                                color = MaterialTheme.colors.secondaryVariant,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            "Enabled: ",
                            style = MaterialTheme.typography.h4
                        )
                        Checkbox(
                            checked = alarm.isEnabled,
                            onCheckedChange = {
                                onEvent(AddEditEvent.OnCheckedEnabled(alarm.isEnabled))
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.onSecondary)
                            .border(
                                width = 3.dp,
                                color = MaterialTheme.colors.secondaryVariant,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            "Enabled: ",
                            style = MaterialTheme.typography.h4
                        )
                        DropDownMenuComponent(melodyDropDownMenuPar)//listof melodies
                    }
                }
            }
        }
    }
}