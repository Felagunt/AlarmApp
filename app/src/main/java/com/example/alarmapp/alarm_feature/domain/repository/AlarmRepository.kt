package com.example.alarmapp.alarm_feature.domain.repository

import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.domain.model.Melody

interface AlarmRepository {

    fun getAlarms(): List<Alarm>

    suspend fun getAlarmById(id: Int): Alarm?

    suspend fun insertAlarm(alarm: Alarm)

    suspend fun deleteAlarm(alarm: Alarm)

    suspend fun getMelodyById(id: Int): Melody?

    suspend fun insertMelody(melody: Melody)

    suspend fun deleteMelody(melody: Melody)

    fun getMelodies(): List<Melody>

    suspend fun getMelodyByName(name: String): Melody?

    suspend fun getMelodyWithAlarms(): List<Melody>
}