package com.example.alarmapp.alarm_feature.data.mapper

import com.example.alarmapp.alarm_feature.data.entity.AlarmEntity
import com.example.alarmapp.alarm_feature.domain.model.Alarm

fun Alarm.toAlarmEntity(): AlarmEntity {
    return AlarmEntity(
        alarmId = alarmId,
        ringsTime = ringsTime.toEpochSecond(),
        isVibration = isVibration,
        melodyId = melodyId,
        isEnabled = isEnabled
    )
}