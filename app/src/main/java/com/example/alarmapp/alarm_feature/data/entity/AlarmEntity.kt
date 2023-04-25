package com.example.alarmapp.alarm_feature.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.Timestamp
import java.time.LocalDateTime


@Entity
data class AlarmEntity(
    @PrimaryKey val alarmId: Int? = null,
    val ringsTime: Long,
    val isVibration: Boolean = false,
    val ringMelody: String,
    var isEnabled: Boolean = false
)

