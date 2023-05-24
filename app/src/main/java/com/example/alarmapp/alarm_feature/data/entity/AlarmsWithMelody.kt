package com.example.alarmapp.alarm_feature.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.alarmapp.alarm_feature.domain.model.Melody

data class AlarmsWithMelody(
    @Embedded val alarmEntity: AlarmEntity,
    @Relation(
        parentColumn = "alarmId",
        entityColumn = "melodyId",
        associateBy = Junction(AlarmMelodyEntity::class)
    )
    val melody: Melody
)
