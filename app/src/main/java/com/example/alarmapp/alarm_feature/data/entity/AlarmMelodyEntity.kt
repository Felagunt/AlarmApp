package com.example.alarmapp.alarm_feature.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["alarmId", "melodyId"])
data class AlarmMelodyEntity(
    val alarmId: Int? = null,
    val melodyId: Int
)
