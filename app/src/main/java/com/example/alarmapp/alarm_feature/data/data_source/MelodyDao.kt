package com.example.alarmapp.alarm_feature.data.data_source

import androidx.room.*
import com.example.alarmapp.alarm_feature.data.entity.MelodyEntity

@Dao
interface MelodyDao {

    @Query("SELECT * FROM melodyentity")
    fun getMelodies(): List<MelodyEntity>

    @Query("SELECT * FROM melodyentity WHERE melodyId = :id")
    suspend fun getMelodyById(id: Int): MelodyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMelody(melodyEntity: MelodyEntity)

    @Delete
    suspend fun deleteMelody(alarmEntity: MelodyEntity)
}