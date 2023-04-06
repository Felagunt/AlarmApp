package com.example.alarmapp.alarm_feature.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alarmapp.alarm_feature.data.entity.AlarmEntity

@Database(
    entities = [AlarmEntity::class],
    version = 1
)
abstract class AlarmDatabase: RoomDatabase() {

    abstract val alarmDao: AlarmDao

    companion object {
        const val DATABASE_NAME = "alarms_db"
    }
}