package com.example.alarmapp.alarmManager_feature.domain

interface AlarmScheduler {
    fun scheduler(item: AlarmItem)

    fun cancel(item: AlarmItem)
}