package com.example.alarmapp.alarm_feature.data.repository

import com.example.alarmapp.alarm_feature.data.data_source.AlarmDao
import com.example.alarmapp.alarm_feature.data.mapper.toAlarm
import com.example.alarmapp.alarm_feature.data.mapper.toAlarmEntity
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao
): AlarmRepository{

    override fun getAlarms(): List<Alarm> {
        return alarmDao.getAlarms().map { it.toAlarm() }
    }

    override suspend fun getAlarmById(id: Int): Alarm? {
        return alarmDao.getAlarmById(id)?.toAlarm()
    }


    override suspend fun insertAlarm(alarm: Alarm) {
        alarmDao.insertAlarm(alarm.toAlarmEntity())
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        alarmDao.deleteAlarm(alarm.toAlarmEntity())
    }
}