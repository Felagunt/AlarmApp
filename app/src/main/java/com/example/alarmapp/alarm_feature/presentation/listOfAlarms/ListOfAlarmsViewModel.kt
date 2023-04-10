package com.example.alarmapp.alarm_feature.presentation.listOfAlarms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.domain.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ListOfAlarmsViewModel(
    private val alarmRepository: AlarmRepository
): ViewModel() {

    var state by mutableStateOf(AlarmsState(emptyList()))
        private set

    private val alarmEventChannel = Channel<AlarmEvent>()
    val alarmEvent = alarmEventChannel.receiveAsFlow()

    private var recentlyDeleted: Alarm? = null

    init {
        getAlarms()
    }

    fun onEvent(event: AlarmEvent) {
        when(event) {
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
            is AlarmEvent.OnAddAlarmClick -> {
                //TODO
            }
        }
    }

    private fun getAlarms() {
        viewModelScope.launch {
            state = state.copy(
                alarms = alarmRepository.getAlarms()
            )
        }
    }
}