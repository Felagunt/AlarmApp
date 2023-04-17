package com.example.alarmapp.alarmManager_feature.domain

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val message: String
)
