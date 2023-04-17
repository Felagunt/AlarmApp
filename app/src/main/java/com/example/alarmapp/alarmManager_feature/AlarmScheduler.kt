package com.example.alarmapp.alarmManager_feature

interface AlarmScheduler {
    fun scheduler(item: AlarmItem)

    fun cancel(item: AlarmItem)
}