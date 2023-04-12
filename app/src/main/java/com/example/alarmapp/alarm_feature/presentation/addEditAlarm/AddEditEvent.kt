package com.example.alarmapp.alarm_feature.presentation.addEditAlarm

import com.example.alarmapp.alarm_feature.domain.model.Alarm

sealed class AddEditEvent {
    data class OnChangeTime(val ringTime: String): AddEditEvent()
    data class OnCheckedVibration(val isEnabled: Boolean): AddEditEvent()
    data class OnChangeMelody(val alarm: Alarm): AddEditEvent()
    object SaveAlarm: AddEditEvent()
}
