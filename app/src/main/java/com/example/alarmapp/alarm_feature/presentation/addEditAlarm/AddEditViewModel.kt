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
import com.example.alarmapp.alarm_feature.presentation.listOfAlarms.AlarmEvent
import com.example.alarmapp.core.presentation.ScreenRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.*
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

    private val alarmEventChannel = Channel<AddEditEvent>()
    val addEditEvent = alarmEventChannel.receiveAsFlow()

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
        when (event) {
            is AddEditEvent.OnChangeMelody -> {

            }
            is AddEditEvent.OnChangeHours -> {
                setTime(
                    hours = event.hours,
                    minutes = null
                )
            }
            is AddEditEvent.OnChangeMinutes -> {
                setTime(
                    hours = null,
                    minutes = event.minutes
                )
            }
            is AddEditEvent.OnChangeTime -> {
                state.alarm = state.alarm?.copy(
                    //ringsTime = event.ringTime
                )
            }
            is AddEditEvent.OnCheckedEnabled -> {
                state.alarm = state.alarm?.copy(
                    isEnabled = event.isEnabled
                )
            }
            is AddEditEvent.OnCheckedVibration -> {
                state.alarm = state.alarm?.copy(
                    isVibration = event.isVibrating
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
                        _eventFlow.send(UiEvent.SaveAlarm)
                        _eventFlow.send(
                            UiEvent.Navigate(
                                _eventFlow.send(
                                    UiEvent.Navigate(
                                        ScreenRoutes.ListOfAlarmsScreen.route
                                    )
                                ).toString()
                            )
                        )
                    } catch (e: Exception) {
                        _eventFlow.send(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Error save"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun setTime(hours: Int?, minutes: Int?) {
        lateinit var zdt: ZonedDateTime
        val targetLocaltime =
            if (minutes != null && hours != null) {
                LocalTime.of(hours, minutes)
            } else {
                LocalTime.now().plusMinutes(10)
            }

        val z = ZoneId.systemDefault()
        val now = ZonedDateTime.now(z)
        val runToday = now.toLocalTime().isBefore(targetLocaltime)

        zdt = if (runToday) {
            now.with(targetLocaltime)
        } else {
            now.toLocalDate().plusDays(1).atStartOfDay(z).with(targetLocaltime)
        }

        state.alarm = state.alarm?.copy(
            ringsTime = zdt
        )

    }

}