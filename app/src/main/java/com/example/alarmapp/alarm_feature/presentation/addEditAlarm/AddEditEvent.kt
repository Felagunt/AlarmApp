package com.example.alarmapp.alarm_feature.presentation.addEditAlarm

import com.example.alarmapp.alarm_feature.domain.model.Alarm
import java.time.LocalDateTime

sealed class AddEditEvent {
    data class OnChangeTime(val ringTime: LocalDateTime): AddEditEvent()
    data class OnCheckedVibration(val isVibrating: Boolean): AddEditEvent()
    data class OnCheckedEnabled(val isEnabled: Boolean): AddEditEvent()
    data class OnChangeMelody(val alarm: Alarm): AddEditEvent()
    data class OnChangeHours(val hours: Int): AddEditEvent()
    data class OnChangeMinutes(val minutes: Int): AddEditEvent()
    object SaveAlarm: AddEditEvent()
}
