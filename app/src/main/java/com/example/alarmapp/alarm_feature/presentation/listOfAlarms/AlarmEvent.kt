package com.example.alarmapp.alarm_feature.presentation.listOfAlarms

import com.example.alarmapp.alarm_feature.domain.model.Alarm

sealed class AlarmEvent {

    data class OnDeleteAlarmClick(val alarm: Alarm): AlarmEvent()
    object OnRestoreAlarmClick: AlarmEvent()
    object OnAddAlarmClick: AlarmEvent()
    data class OnEnableClick(val alarm: Alarm, val isEnabled: Boolean): AlarmEvent()
    data class OnAlarmClick(val alarm: Alarm): AlarmEvent()
}