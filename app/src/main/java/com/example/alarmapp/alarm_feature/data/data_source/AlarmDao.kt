package com.example.alarmapp.alarm_feature.data.data_source

import androidx.room.*
import com.example.alarmapp.alarm_feature.data.entity.AlarmEntity
import com.example.alarmapp.alarm_feature.data.entity.AlarmMelodyCrossRef
import com.example.alarmapp.alarm_feature.data.entity.MelodyWithAlarms

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarmentity")
    fun getAlarms(): List<AlarmEntity>

    @Query("SELECT * FROM alarmentity WHERE alarmId = :id")
    suspend fun getAlarmById(id: Int): AlarmEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmEntity: AlarmEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarmMelody(alarmMelodyCrossRef: AlarmMelodyCrossRef
        //orderProductEntities: List<OrderProductEntity>
        )

    @Delete
    suspend fun deleteAlarm(alarmEntity: AlarmEntity)

    @Transaction
    @Query("SELECT * FROM alarmentity")
    fun getAlarmsWithMelody(): List<MelodyWithAlarms>
}