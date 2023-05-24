package com.example.alarmapp.alarm_feature.data.mapper

import com.example.alarmapp.alarm_feature.data.entity.AlarmsWithMelody
import com.example.alarmapp.alarm_feature.data.entity.MelodyWithAlarms
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import com.example.alarmapp.alarm_feature.domain.model.Melody
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

fun MelodyWithAlarms.toMelody(): Melody {
    return Melody(
        melodyId = melodyEntity.melodyId,
        melodyPath = melodyEntity.melodyPath ?: "",
        melodyName = melodyEntity.melodyName ?: "",
    )
//    return Alarm(
//        ringMelody = melodyEntity.ringMelody ?: "",
//        alarmId = alarms.zip()
//    )

}

fun AlarmsWithMelody.toAlarm(): Alarm {
    return Alarm(
        alarmId = alarmEntity.alarmId,
        isEnabled = alarmEntity.isEnabled,
        isVibration = alarmEntity.isVibration,
        ringsTime = ZonedDateTime.ofInstant(
            Instant.ofEpochSecond(alarmEntity.ringsTime),
            ZoneId.systemDefault()
        ),
        melody = melody
    )
}