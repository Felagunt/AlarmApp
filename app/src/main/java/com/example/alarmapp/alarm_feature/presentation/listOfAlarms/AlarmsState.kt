package com.example.alarmapp.alarm_feature.presentation.listOfAlarms

import com.example.alarmapp.alarm_feature.domain.model.Alarm

data class AlarmsState(
    val alarms: List<Alarm>
)
