package com.example.alarmapp.alarmManager_feature

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val message: String
)
