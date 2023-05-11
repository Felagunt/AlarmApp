package com.example.alarmapp.alarmManager_feature

import com.example.alarmapp.alarm_feature.domain.model.Alarm

interface AlarmScheduler {
    fun scheduler(item: Alarm)

    fun cancel(item: Alarm)
}