package com.example.alarmapp.alarm_feature.data.mapper

import com.example.alarmapp.alarm_feature.data.entity.AlarmEntity
import com.example.alarmapp.alarm_feature.data.entity.MelodyWithAlarms
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.domain.model.Melody
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

fun AlarmEntity.toAlarm(melody: Melody): Alarm {
    return Alarm(
        ringsTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(ringsTime), ZoneId.systemDefault()),
        alarmId = alarmId,
        isVibration = isVibration,
        isEnabled = isEnabled,
        melody = melody
    )
}