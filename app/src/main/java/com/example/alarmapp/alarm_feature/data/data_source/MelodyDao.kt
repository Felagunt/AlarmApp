package com.example.alarmapp.alarm_feature.data.data_source

import androidx.room.*
import com.example.alarmapp.alarm_feature.data.entity.MelodyEntity
import com.example.alarmapp.alarm_feature.data.entity.MelodyWithAlarms

@Dao
interface MelodyDao {

    @Query("SELECT * FROM melodyentity")
    fun getMelodies(): List<MelodyEntity>

    @Query("SELECT * FROM melodyentity WHERE melodyId = :id")
    suspend fun getMelodyById(id: Int): MelodyEntity?

    @Query("Select melodyName FROM melodyentity WHERE melodyId = :id")
    suspend fun getMelodyNameById(id: Int): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMelody(melodyEntity: MelodyEntity)

    @Delete
    suspend fun deleteMelody(alarmEntity: MelodyEntity)

    @Transaction
    @Query("SELECT * FROM melodyentity")
    suspend fun getMelodiesWithAlarms(): List<MelodyWithAlarms>
}