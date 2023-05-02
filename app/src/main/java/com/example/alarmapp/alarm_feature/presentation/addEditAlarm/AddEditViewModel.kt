package com.example.alarmapp.alarm_feature.presentation.addEditAlarm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.domain.repository.AlarmRepository
import com.example.alarmapp.alarm_feature.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
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

        zdt = if (runToday){
            now.with(targetLocaltime)
        }
        else{
            now.toLocalDate().plusDays(1).atStartOfDay(z).with(targetLocaltime)
        }

        state.alarm = state.alarm?.copy(
            ringsTime = zdt
        )

    }

}