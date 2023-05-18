package com.example.alarmapp.alarm_feature.data.repository

import com.example.alarmapp.alarm_feature.data.data_source.AlarmDao
import com.example.alarmapp.alarm_feature.data.data_source.MelodyDao
import com.example.alarmapp.alarm_feature.data.entity.AlarmMelodyCrossRef
import com.example.alarmapp.alarm_feature.data.mapper.toAlarm
import com.example.alarmapp.alarm_feature.data.mapper.toAlarmEntity
import com.example.alarmapp.alarm_feature.data.mapper.toMelody
import com.example.alarmapp.alarm_feature.data.mapper.toMelodyEntity
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.domain.model.Melody
import com.example.alarmapp.alarm_feature.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao,
    private val melodyDao: MelodyDao
): AlarmRepository{

    override fun getAlarms(): List<Alarm> {
        return alarmDao.getAlarms().map { it.toAlarm() }
    }

    override suspend fun getAlarmById(id: Int): Alarm? {
        return alarmDao.getAlarmById(id)?.toAlarm()
    }

    override suspend fun insertAlarm(alarm: Alarm) {
        alarmDao.insertAlarm(alarm.toAlarmEntity())
        val alarmMelody =
                AlarmMelodyCrossRef(
                    alarmId = alarm.alarmId ,
                    melodyId = alarm.melodyId
                )
        alarmDao.insertAlarmMelody(alarmMelody)
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        alarmDao.deleteAlarm(alarm.toAlarmEntity())
    }

    override suspend fun getMelodyById(id: Int): Melody? {
        return melodyDao.getMelodyById(id)?.toMelody()
    }

    override suspend fun insertMelody(melody: Melody) {
        melodyDao.insertMelody(melody.toMelodyEntity())
    }

    override suspend fun deleteMelody(melody: Melody) {
        melodyDao.deleteMelody(melody.toMelodyEntity())
    }

    override fun getMelodies(): List<Melody> {
        return melodyDao.getMelodies().map { it.toMelody() }
    }

    override suspend fun getMelodyNameById(id: Int): String {
        return melodyDao.getMelodyNameById(id)
    }

    override suspend fun getMelodyWithAlarms(): List<Melody> {
        return melodyDao.getMelodiesWithAlarms().map { it.toMelody() }
    }
}