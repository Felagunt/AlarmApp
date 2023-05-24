package com.example.alarmapp.alarmManager_feature.presetation.fires_alarms

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.alarmManager_feature.data.AndroidAlarmScheduler
import com.example.alarmapp.alarm_feature.data.mapper.toAlarm
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.domain.repository.AlarmRepository
import com.example.alarmapp.alarm_feature.util.UiEvent
import com.example.alarmapp.core.presentation.ScreenRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class FireAlarmsViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var fireAlarm: Alarm? = null


    //val scheduler = AndroidAlarmScheduler(context = Context)




    private val _eventFlow = Channel<UiEvent>()
    val uiEventFlow = _eventFlow.receiveAsFlow()
    init {
        savedStateHandle.get<Int>("alarmId")?.let { alarmId ->
            if (alarmId != -1) {
                viewModelScope.launch {
                    fireAlarm = alarmRepository.getAlarmById(alarmId)
                }
            }
        }
    }

    fun onEvent(event: FireAlarmsEvent) {
        when(event) {
            is FireAlarmsEvent.onAwaitClick -> {
                //TODO scheduler alarm + 10 minutes
            }
            is FireAlarmsEvent.onCancel -> {
                //TODO cancel alarm
                //alarmItem?.let(scheduler::cancel)
                sendEvent(UiEvent.Navigate(ScreenRoutes.FinaleScreen.route))
            }
        }
    }


    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}