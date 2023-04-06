package com.example.alarmapp.alarm_feature.domain.model

import androidx.room.PrimaryKey

data class Alarm(
    val alarmId: Int? = null,
    val ringsTime: String,
    val isVibration: Boolean = false,
    val ringMelody: String
)
