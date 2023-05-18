package com.example.alarmapp.alarm_feature.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MelodyEntity(
    @PrimaryKey val melodyId: Int,
    //val alarmId: Int? = null,
    val melodyName: String? = "",
    val melodyPath: String? = ""
)