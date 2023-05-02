package com.example.alarmapp.alarm_feature.presentation.listOfAlarms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.domain.repository.AlarmRepository
import com.example.alarmapp.alarm_feature.util.UiEvent
import com.example.alarmapp.core.presentation.ScreenRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class ListOfAlarmsViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository
) : ViewModel() {

    var state by mutableStateOf(AlarmsState(emptyList()))
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    private var recentlyDeleted: Alarm? = null

    //    var nextAlarmTime by remember {
//        mutableStateOf(
//            getNearestTime()
//        )
//    }
    var nextAlarmTime by mutableStateOf(getNearestTime())

    init {
        getAlarms()
    }

    fun onEvent(event: AlarmEvent) {
        when (event) {
            is AlarmEvent.OnAlarmClick -> {
                //TODO
                sendUiEvent(
                    UiEvent.Navigate(
                        ScreenRoutes.AddEditAlarmScreen.route + "?alarmId=${event.alarm.alarmId}"
                    )
                )
            }
            is AlarmEvent.OnDeleteAlarmClick -> {
                viewModelScope.launch {
                    recentlyDeleted = event.alarm
                    alarmRepository.deleteAlarm(event.alarm)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Alarm deleted",
                        action = "Undo"
                    ))
                }
            }
            is AlarmEvent.OnRestoreAlarmClick -> {
                recentlyDeleted?.let {alarm ->
                    viewModelScope.launch {
                        alarmRepository.insertAlarm(alarm)
                        recentlyDeleted = null
                    }
                }
            }
            is AlarmEvent.OnEnableClick -> {
                viewModelScope.launch {
                    alarmRepository.insertAlarm(
                        event.alarm.copy(
                            isEnabled = event.isEnabled
                        )
                    )
                    event.alarm.isEnabled = event.alarm.isEnabled
                }
            }
            is AlarmEvent.OnAddAlarmClick -> {
                //TODO
                sendUiEvent(UiEvent.Navigate(ScreenRoutes.AddEditAlarmScreen.route))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun getNearestTime(): LocalDateTime? {

        val alarm = state.alarms.sortedWith(compareBy { it.ringsTime }).first()
        val ringsTime = alarm.ringsTime
        val currentTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
        val nextAlarm = LocalDateTime.now()
        val eta = java.time.Duration.between(
            currentTime.toInstant(),
            ringsTime.toInstant()
        ).toMinutes()
        if (eta > 60) {
            val hours = eta / 60
            val minutes = eta % 60
            nextAlarm.plusHours(eta / 60).plusMinutes(eta % 60)
            print("$hours:$minutes remaining")
        } else {
            nextAlarm.plusMinutes(eta)
            print("$eta minutes remaining")
        }

        return nextAlarm
    }

    private fun getAlarms() {
        viewModelScope.launch {
            state = state.copy(
                alarms = alarmRepository.getAlarms()
            )
        }
    }
}