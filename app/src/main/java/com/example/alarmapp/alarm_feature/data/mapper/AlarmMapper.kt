package com.example.alarmapp.alarm_feature.data.mapper

import com.example.alarmapp.alarm_feature.data.entity.AlarmEntity
import com.example.alarmapp.alarm_feature.domain.model.Alarm

fun AlarmEntity.toAlarm(): Alarm {
    return Alarm(
        ringsTime = ringsTime,
        alarmId = alarmId,
        isVibration = isVibration,
        ringMelody = ringMelody,
        isEnabled = isEnabled
    )
}