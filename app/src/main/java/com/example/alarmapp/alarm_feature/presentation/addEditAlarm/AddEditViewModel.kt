package com.example.alarmapp.alarm_feature.presentation.addEditAlarm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.domain.repository.AlarmRepository
import com.example.alarmapp.alarm_feature.presentation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@HiltViewModel
class AddEditViewModel(
    private val alarmRepository: AlarmRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(AddEditAlarmState())
        private set

    private val _eventFlow = Channel<UiEvent>()
    val alarmEvent = _eventFlow.receiveAsFlow()

    private var currentAlarmId: Int? = null

    init {
        savedStateHandle.get<Int>("alarmId")?.let { alarmId ->
            if (alarmId != -1) {
                viewModelScope.launch {
                    alarmRepository.getAlarmById(alarmId)?.also { alarm ->
                        currentAlarmId = alarm.alarmId
                        state = state.copy(
                            alarm = alarm
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when(event) {
            is AddEditEvent.OnChangeMelody -> {

            }
            is AddEditEvent.OnChangeTime -> {
                state.alarm = state.alarm?.copy(
                    ringsTime = event.ringTime
                )
            }
            is AddEditEvent.OnCheckedVibration -> {
                state.alarm = state.alarm?.copy(
                    isVibration = event.isEnabled
                )
            }
            is AddEditEvent.SaveAlarm -> {
                val currentTime =
                    LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                    )
                viewModelScope.launch {
                    try {
                        state.alarm?.let {
                            Alarm(
                                ringsTime = it.ringsTime,
                                isVibration = it.isVibration,
                                isEnabled = it.isEnabled,
                                ringMelody = it.ringMelody,
                                alarmId = it.alarmId
                            )
                        }?.let {
                            alarmRepository.insertAlarm(
                                it
                            )
                        }
                        _eventFlow.trySend(UiEvent.SaveAlarm)
                    } catch (e: Exception){
                     _eventFlow.trySend(
                         UiEvent.ShowSnackbar(
                             message = e.message ?: "Error save"
                         )
                     )
                    }
                }
            }
        }
    }

}