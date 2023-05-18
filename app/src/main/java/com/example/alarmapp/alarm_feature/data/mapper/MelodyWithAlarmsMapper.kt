package com.example.alarmapp.alarm_feature.data.mapper

import com.example.alarmapp.alarm_feature.data.entity.MelodyWithAlarms
import com.example.alarmapp.alarm_feature.domain.model.Melody

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