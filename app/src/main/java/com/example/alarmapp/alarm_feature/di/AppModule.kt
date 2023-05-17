package com.example.alarmapp.alarm_feature.di

import android.content.Context
import androidx.room.Room
import com.example.alarmapp.alarm_feature.data.data_source.AlarmDatabase
import com.example.alarmapp.alarm_feature.data.repository.AlarmRepositoryImpl
import com.example.alarmapp.alarm_feature.domain.repository.AlarmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAlarmDatabase(@ApplicationContext context: Context): AlarmDatabase {
        return Room.databaseBuilder(
            context,
            AlarmDatabase::class.java,
            AlarmDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlarmRepository(db: AlarmDatabase): AlarmRepository {
        return AlarmRepositoryImpl(db.alarmDao, db.melodyDao)
    }
}