package com.example.alarmapp.alarm_feature.presentation.addEditAlarm

import com.example.alarmapp.alarm_feature.domain.model.Alarm
import java.time.LocalDateTime

sealed class AddEditEvent {
    data class OnChangeTime(val ringTime: LocalDateTime): AddEditEvent()
    data class OnCheckedVibration(val alarm: Alarm, val isVibrating: Boolean): AddEditEvent()
    data class OnCheckedEnabled(val alarm: Alarm, val isEnabled: Boolean): AddEditEvent()
    data class OnChangeMelody(val alarm: Alarm, val melodyId: Int): AddEditEvent()
    data class OnChangeHours(val hours: Int): AddEditEvent()
    data class OnChangeMinutes(val minutes: Int): AddEditEvent()
    object OnSaveAlarm: AddEditEvent()
}
