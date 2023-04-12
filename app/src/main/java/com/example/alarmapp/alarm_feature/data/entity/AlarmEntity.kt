package com.example.alarmapp.alarm_feature.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlarmEntity(
    @PrimaryKey val alarmId: Int? = null,
    val ringsTime: String,
    val isVibration: Boolean = false,
    val ringMelody: String,
    var isEnabled: Boolean = false
)

