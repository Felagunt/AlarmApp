package com.example.alarmapp.alarm_feature.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity
data class AlarmEntity(
    @PrimaryKey val alarmId: Int? = null,
    val ringsTime: ZonedDateTime,
    val isVibration: Boolean = false,
    val ringMelody: String,
    var isEnabled: Boolean = false
)

