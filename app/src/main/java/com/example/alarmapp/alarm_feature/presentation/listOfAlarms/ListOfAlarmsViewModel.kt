package com.example.alarmapp.alarm_feature.presentation.listOfAlarms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.domain.repository.AlarmRepository
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

    private val alarmEventChannel = Channel<AlarmEvent>()
    val alarmEvent = alarmEventChannel.receiveAsFlow()

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
                viewModelScope.launch {
                    event.alarm.alarmId?.let {
                        val result = alarmRepository.getAlarmById(it)
                    }
                }
            }
            is AlarmEvent.OnDeleteAlarmClick -> {
                viewModelScope.launch {
                    alarmRepository.deleteAlarm(event.alarm)
                    recentlyDeleted = event.alarm
                }
            }
            is AlarmEvent.OnRestoreAlarmClick -> {
                viewModelScope.launch {
                    alarmRepository.insertAlarm(recentlyDeleted ?: return@launch)
                    recentlyDeleted = null
                }
            }
            is AlarmEvent.OnEnableClick -> {
                event.alarm.isEnabled = event.alarm.isEnabled
            }
            is AlarmEvent.OnAddAlarmClick -> {
                //TODO
            }
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