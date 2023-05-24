package com.example.alarmapp.alarm_feature.presentation.addEditAlarm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.domain.model.Melody
import com.example.alarmapp.alarm_feature.domain.repository.AlarmRepository
import com.example.alarmapp.alarm_feature.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.*
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(AddEditAlarmState())
        private set

    var melodies by mutableStateOf<List<Melody>>(emptyList())

    private val _eventFlow = Channel<UiEvent>()
    val uiEventFlow = _eventFlow.receiveAsFlow()

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
            getMelodies()
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.OnChangeMelody -> {
                //TODO

                state.alarm = state.alarm?.copy(
                    melody = event.alarm.melody
                )
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
                    ringsTime = event.ringTime.atZone(ZoneId.systemDefault())//TODO
                )
            }
            is AddEditEvent.OnCheckedEnabled -> {
//                state.alarm?.copy(
//                    isEnabled = event.isEnabled
//                )
//                state.alarm = event.alarm.copy(
//                    isEnabled = event.isEnabled
//                )
                state.alarm = state.alarm?.copy(
                    isEnabled = event.isEnabled
                )
            }
            is AddEditEvent.OnCheckedVibration -> {
//                state.alarm?.copy(
//                    isVibration = event.isVibrating
//                )
                state.alarm = state.alarm?.copy(
                    isVibration = event.isVibrating
                )
            }
            is AddEditEvent.OnSaveAlarm -> {
                viewModelScope.launch {
                    if (state.alarm?.ringsTime == ZonedDateTime.now()) {
                        sendEvent(
                            UiEvent.ShowSnackbar(
                                "Choose time"
                            )
                        )
                        return@launch
                    }
                    state.alarm.let {
                        alarmRepository.insertAlarm(
                            Alarm(
                                ringsTime = it?.ringsTime ?:
                                    ZonedDateTime.now().plusMinutes(10),
                                isVibration = it?.isVibration ?: false,
                                isEnabled = it?.isEnabled ?: true,
                                melody = it?.melody,
                                alarmId = it?.alarmId
                            )
                        )
                    }
                    sendEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun getMelodies() {
        viewModelScope.launch {
            melodies = alarmRepository.getMelodies()
        }
    }


    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
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