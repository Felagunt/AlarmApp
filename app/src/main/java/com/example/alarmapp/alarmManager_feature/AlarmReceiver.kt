package com.example.alarmapp.alarmManager_feature

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.alarmapp.alarm_feature.domain.repository.AlarmRepository
import com.example.alarmapp.alarm_feature.presentation.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject
    lateinit var repository: AlarmRepository//here we'll use user fields as a repos etc

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("Extra_message") ?: return
        println("Alarm triggered: $message")
        //UiEvent.navigate()
    }
}