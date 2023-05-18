package com.example.alarmapp.alarm_feature.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MelodyWithAlarms(
    @Embedded val melodyEntity: MelodyEntity,
    @Relation(
        parentColumn = "melodyId",
        entityColumn = "alarmId",
        associateBy = Junction(AlarmMelodyCrossRef::class)
    )
    val alarms: List<AlarmEntity>
)
