package com.example.alarmapp.alarm_feature.domain.model

data class Alarm(
    val alarmId: Int? = null,
    val ringsTime: Long,
    val isVibration: Boolean = false,
    val ringMelody: String,
    var isEnabled: Boolean = true
)
