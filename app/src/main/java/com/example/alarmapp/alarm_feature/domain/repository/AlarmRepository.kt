package com.example.alarmapp.alarm_feature.domain.repository

import com.example.alarmapp.alarm_feature.domain.model.Alarm

interface AlarmRepository {

    fun getAlarms(): List<Alarm>

    suspend fun getAlarmById(id: Int): Alarm?

    suspend fun insertAlarm(alarm: Alarm)

    suspend fun deleteAlarm(alarm: Alarm)
}