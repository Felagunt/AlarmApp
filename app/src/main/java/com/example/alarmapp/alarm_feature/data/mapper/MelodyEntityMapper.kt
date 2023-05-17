package com.example.alarmapp.alarm_feature.data.mapper

import com.example.alarmapp.alarm_feature.data.entity.MelodyEntity
import com.example.alarmapp.alarm_feature.domain.model.Melody

fun Melody.toMelodyEntity(): MelodyEntity {
    return MelodyEntity(
        ringMelody = ringMelody,
        melodyPath = melodyPath,
        melodyId = melodyId
    )
}