package com.example.alarmapp.alarmManager_feature.presetation.fires_alarms

import com.example.alarmapp.alarm_feature.domain.model.Alarm

sealed class FireAlarmsEvent {

    data class  onAwaitClick(val alarm: Alarm): FireAlarmsEvent()
    object onCancel: FireAlarmsEvent()
}
