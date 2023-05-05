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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.alarmapp.alarm_feature.util.UiEvent
import com.example.alarmapp.alarm_feature.presentation.addEditAlarm.AddEditEvent
import com.example.alarmapp.alarm_feature.presentation.addEditAlarm.AddEditViewModel
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditAlarmScreen(
    onPopBackStack: () -> Unit,
    addEditViewModel: AddEditViewModel = hiltViewModel()
) {
    val state = addEditViewModel.state

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
        addEditViewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addEditViewModel.onEvent(AddEditEvent.OnSaveAlarm)
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
                .padding(paddingValues = it)
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
                            addEditViewModel.onEvent(
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
                            addEditViewModel.onEvent(
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
                            addEditViewModel.onEvent(
                                AddEditEvent.OnCheckedEnabled(
                                    alarm,
                                    alarm.isVibration
                                )
                            )
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
                                addEditViewModel.onEvent(AddEditEvent.OnCheckedEnabled(alarm.isEnabled))
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