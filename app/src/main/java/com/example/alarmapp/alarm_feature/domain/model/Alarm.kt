package com.example.alarmapp.alarm_feature.domain.model

import java.time.ZonedDateTime

data class Alarm(
    val alarmId: Int? = null,
    val ringsTime: ZonedDateTime,
    val isVibration: Boolean = false,
    val melody: Melody? = null,
    var isEnabled: Boolean = true
)
